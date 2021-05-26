package com.unionbankng.future.futurejobservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.pojos.PaymentRequest;
import com.unionbankng.future.futurejobservice.repositories.*;
import com.unionbankng.future.futurejobservice.util.App;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JobContractService implements Serializable {

    @Value("${sidekiq.escrow.appId}")
    private int appId;
    @Value("${sidekiq.escrow.token}")
    private String token;
    private String escrowAccountName="M36 FLOAT ACCOUNT ( WITH CURRENCY RESTRICTION AS - ALL)"; //GL
    private String escrowAccountNumber="250990140";
    private String UBNIncomeAccountName = "Marcus Securities Settlement"; //GL
    private String UBNIncomeAccountNumber = "250990144";
    private String PepperestIncomeAccountName = "DEDICATED NEFT O A"; //CASA
    private String PepperestIncomeAccountNumber = "0055982543";

    private String baseURL = "https://pepperest.com/EscrowService/api/Escrow";
    private Logger logger = LoggerFactory.getLogger(JobContractService.class);

    @Autowired
    private RestTemplate rest;
    private final JobRepository jobRepository;
    private final JobContractRepository jobContractRepository;
    private final JobContractExtensionRepository jobContractExtensionRepository;
    private final JobProposalRepository jobProposalRepository;
    private final JobProjectSubmissionRepository jobProjectSubmissionRepository;
    private final JobMilestoneRepository jobMilestoneRepository;
    private final JobRateRepository jobRateRepository;
    private final FileStoreService fileStoreService;
    private final JobPaymentService jobPaymentService;
    private final JobTeamDetailsRepository jobTeamDetailsRepository;
    private final JobContractDisputeRepository jobContractDisputeRepository;
    private final JobPaymentRepository jobPaymentRepository;
    private final NotificationSender notificationSender;
    private final App app;


    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setCacheControl("no-cache");
        headers.add("Token", token);
        return headers;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long decodeDuration(long duration, String type) {
        long[] factor = {1, 7, 30, 360};
        switch (type) {
            case "D":
                return factor[0] * duration;
            case "W":
                return factor[1] * duration;
            case "M":
                return factor[2] * duration;
            case "Y":
                return factor[3] * duration;
            default:
                return duration;
        }
    }

    public Long getTotalJobEarningByUserId(Long userId) {
        return jobContractRepository.getTotalJobEarningByUserId(userId).orElse(0l);
    }

    public Long getTotalJobExpenditureByUser(Long userId) {
        return jobContractRepository.getTotalJobExpenditureByUserId(userId).orElse(0l);
    }

    public Long getTotalJobCompletedByUser(Long userId) {
        return jobContractRepository.getTotalJobCompletedByUser(userId).orElse(0l);
    }

    public JobContract findJobContractByProposalAndJobId(Long proposalId, Long jobId) {
        return jobContractRepository.findContractByProposalAndJobId(proposalId, jobId);
    }

    public JobContractExtension findContractExtensionByProposalId(Long proposalId, Long userId) {
        return jobContractExtensionRepository.findContractByProposalAndUserId(proposalId, userId);
    }

    public JobProjectSubmission findJobSubmittedByProposalId(Long proposalId, Long userId) {
        return jobProjectSubmissionRepository.findSubmittedProjectByProposalAndUserId(proposalId, userId);
    }

    public JobMilestone findContractMilestoneByProposalId(Long proposalId, Long userId) {
        return jobMilestoneRepository.findMilestoneByProposalAndJobId(proposalId, userId);
    }

    public JobMilestone findMilestoneById(Long milestoneId) {
        return jobMilestoneRepository.findById(milestoneId).orElse(null);
    }

    public JobMilestone findContractMilestoneByJobId(Long proposalId, Long jobId) {
        return jobMilestoneRepository.findMilestoneByProposalAndJobId(proposalId, jobId);
    }

    public Long findTotalSpentAmountByProposalId(Long proposalId, Long jobId) {
        return jobMilestoneRepository.findTotalSpentAmountByProposalId(proposalId, jobId);
    }

    public List<JobMilestone> findAllContractMilestoneByProposalJobIdAndStatus(Long proposalId, Long jobId, String status) {
        return jobMilestoneRepository.findAllMilestonesByProposalJobAndStatus(proposalId, jobId, status);
    }

    public List<JobMilestone> findAllContractMilestoneByProposalJobId(Long proposalId, Long jobId) {
        return jobMilestoneRepository.findAllMilestonesByProposalAndJobId(proposalId, jobId);
    }

    public APIResponse approveJobProposal(Principal principal, String request) throws JsonProcessingException {

        JobContract contract = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContract.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return approveJobProposal(currentUser, contract);
    }

    public APIResponse approveJobProposal(JwtUserDetail currentUser, JobContract contract) {
        try {
            JobProposal proposal = jobProposalRepository.findById(contract.getProposalId()).orElse(null);
            Job job = jobRepository.findById(proposal.getJobId()).orElse(null);
            String contractReferenceId = app.makeUIID();
            String paymentReferenceId = app.makeUIID();

            contract.setPeppfees(0); //set peprest charges to zero
            contract.setContractReference(contractReferenceId);
            contract.setInitialPaymentReferenceA(paymentReferenceId);
            contract.setAppId(appId);

            int status = 0;
            String remark = null;

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, proposal.duration.intValue());
            contract.setEndDate(c.getTime());
            contract.setStartDate(new Date());
            contract.setStatus(JobStatus.WP);
            proposal.setStatus(JobStatus.WP);
            job.setStatus(JobStatus.WP);
            proposal.setLastModifiedDate(new Date());
            proposal.setStartDate(new Date());
            proposal.setEndDate(c.getTime());

            if (contract.getWorkMethod() == "Milestone") {
                status = 1;
                //fire notification
                if (job != null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(currentUser.getUserFullName() + " approved your contract and the amount will be paid to you base on the milestone you complete");
                    body.setSubject("Proposal Approval");
                    body.setActionType("REDIRECT");
                    body.setAction("/job/ongoing/details/" + proposal.getJobId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setPriority("YES");
                    body.setRecipient(proposal.getUserId());
                    notificationSender.pushNotification(body);
                }
                //end
            } else {
                //transfer the amount to Sidekiq main account
                PaymentRequest payment = new PaymentRequest();
                payment.setProposalId(contract.getProposalId());
                payment.setAmount(contract.getAmount());
                payment.setNarration("Transfer from Employer to Escrow account for job Contract");
                payment.setCreditAccountName(escrowAccountName);
                payment.setCreditAccountNumber(escrowAccountNumber);
                payment.setDebitAccountName(proposal.getAccountName());
                payment.setDebitAccountNumber(proposal.getAccountNumber());
                payment.setPaymentReference(contractReferenceId);
                payment.setExecutedFor("Job:"+job.getId());
                payment.setExecutedBy(currentUser.getUserUUID());

                APIResponse paymentResponse=jobPaymentService.makePayment(payment);
                if (paymentResponse.isSuccess()) {
                    contract.setInitialPaymentReferenceB(paymentResponse.getPayload().toString());
                    if (job != null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody("Your payment of " + contract.getAmount() + " has been successful");
                        body.setSubject("Payment Successful");
                        body.setActionType("REDIRECT");
                        body.setAction("/job/ongoing/details/" + proposal.getJobId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setPriority("YES");
                        body.setRecipient(proposal.getEmployerId());
                        notificationSender.pushNotification(body);
                    }

                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/create?appid=" + appId
                            + "&referenceid=" + contract.getContractReference()
                            + "&user_email=" + contract.getUserEmail()
                            + "&amount=" + contract.getAmount()
                            + "&country=" + contract.getCountry()
                            + "&currency=" + contract.getCurrency()
                            + "&customer_email=" + contract.getFreelancerEmailAddress()
                            + "&merchant_email=" + contract.getEmployerEmailAddress()
                            + "&customer_account_number=" + contract.getFreelancerAccountNumber()
                            + "&merchant_account_number=" + contract.getEmployerAccountNumber()
                            + "&customer_bank_code=032"
                            + "&merchant_bank_code=032"
                            + "&customer_name=" + contract.getFreelancerAccountName()
                            + "&merchant_name=" + contract.getEmployerAccountName()
                            + "&customer_phone=" + contract.getFreelancerPhoneNumber()
                            + "&merchant_phone=" + contract.getEmployerPhoneNumber()
                            + "&peppfees=" + contract.getPeppfees()
                            + "&startdate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(contract.getStartDate())
                            + "&enddate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(contract.getEndDate())
                            + "&transfer_reference_id=" + contract.getReversalPaymentReferenceA(), HttpMethod.POST, entity, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        status = 1;
                        remark = "success";

                        //fire notifications
                        NotificationBody body1 = new NotificationBody();
                        body1.setBody("Your " + contract.getAmount() + " deducted is currently in our Escrow, it will only be released to the freelancer when you confirm that the job done is okay by you.");
                        body1.setSubject("Contract Amount is in Escrow");
                        body1.setActionType("REDIRECT");
                        body1.setAction("/job/ongoing/details/" + proposal.getJobId());
                        body1.setTopic("'Job'");
                        body1.setChannel("S");
                        body1.setPriority("YES");
                        body1.setRecipient(proposal.getEmployerId());
                        notificationSender.pushNotification(body1);

                        NotificationBody body2 = new NotificationBody();
                        body2.setBody(currentUser.getUserFullName() + " approved your contract and credited our escrow with the sum of " + proposal.getBidAmount());
                        body2.setSubject("Proposal Approval");
                        body2.setActionType("REDIRECT");
                        body2.setAction("/job/ongoing/details/" + proposal.getJobId());
                        body2.setTopic("'Job'");
                        body2.setChannel("S");
                        body2.setPriority("YES");
                        body2.setRecipient(proposal.getUserId());
                        notificationSender.pushNotification(body2);

                        //end
                    } else {
                        status = 0;
                        remark = "Escrow transaction failed";
                        logger.info("JOBSERVICE: Escrow transaction failed");
                        logger.error("JOBSERVICE: Escrow " + response.getBody());

                    }
                } else {
                    status = 0;
                    remark =paymentResponse.getMessage();
                }
            }
            if (status == 1) {
                Optional<Job> jobData = jobRepository.findById(contract.getJobId());
                if (job != null) {
                    job.setStatus(JobStatus.WP);
                    job.setLastModifiedDate(new Date());
                    jobRepository.save(job);

                    if (job.getType() == JobType.TEAMS_PROJECT) {

                        JobTeamDetails existingMember = jobTeamDetailsRepository.findByProposalId(proposal.getId());
                        if (existingMember == null) {
                            // save approved team member
                            JobTeamDetails teamMember = new JobTeamDetails();
                            teamMember.setImg(proposal.getImg());
                            teamMember.setFullName(proposal.getFullName());
                            teamMember.setEmail(proposal.getEmail());
                            teamMember.setAmount(proposal.bidAmount);
                            teamMember.setJobId(proposal.jobId);
                            teamMember.setUserId(proposal.getUserId());
                            teamMember.setProposalId(proposal.id);
                            teamMember.setEmployerId(proposal.employerId);
                            teamMember.setStatus(JobStatus.AC);
                            teamMember.setDescription(proposal.about);
                            teamMember.setPercentage(Long.valueOf(10));
                            teamMember.setAmount(proposal.bidAmount);
                            teamMember.setStartDate(new Date());
                            teamMember.setEndDate(c.getTime());
                            jobTeamDetailsRepository.save(teamMember);
                        } else {
                            existingMember.setStartDate(new Date());
                            existingMember.setEndDate(c.getTime());
                            existingMember.setStatus(JobStatus.AC);
                            jobTeamDetailsRepository.save(existingMember);
                        }
                    }
                } else {
                    logger.info("JOBSERVICE: Job not found");
                    remark = "Job not found";
                }

                JobContract savedContract = jobContractRepository.save(contract);
                proposal.setContractId(savedContract.getId());
                jobProposalRepository.save(proposal);
                jobRepository.save(job);
                return new APIResponse(remark, true, savedContract);
            } else {
                logger.info("JOBSERVICE: Transaction failed");
                return new APIResponse(remark, false, null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("Oops! An Error Occurred", false, null);
        }
    }

    public JobContractExtension requestContractExtension(Principal principal, String request) throws JsonProcessingException {

        JobContractExtension extensionRequest = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContractExtension.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return requestContractExtension(currentUser.getUserFullName(), extensionRequest);

    }

    public JobContractExtension requestContractExtension(String userName, JobContractExtension extensionRequest) {
        try {
            extensionRequest.setStatus(JobStatus.PE);
            JobContractExtension extension = jobContractExtensionRepository.save(extensionRequest);

            if (extension != null) {
                //fire notification
                Job currentJob = jobRepository.findById(extension.getJobId()).orElse(null);
                if (currentJob != null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(userName + " want you to help extend delivery date for " + currentJob.getTitle() + " to " + extension.getDate().toString());
                    body.setSubject("Contract Extension");
                    body.setActionType("REDIRECT");
                    body.setAction("/job/ongoing/details/" + extension.getJobId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setRecipient(extension.getEmployerId());
                    notificationSender.pushNotification(body);
                }
                //end
                return extension;
            } else {
                logger.info("JOBSERVICE: Unable to submit the contract extension");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JobMilestone addNewMilestone(Principal principal, String request) throws JsonProcessingException {
        JobMilestone milestone = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobMilestone.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return addNewMilestone(currentUser.getUserFullName(), milestone);
    }


    public JobMilestone addNewMilestone(String userName, JobMilestone milestone) {
        try {
            milestone.setStatus(JobStatus.PE);
            JobMilestone newMilestone = jobMilestoneRepository.save(milestone);
            if (newMilestone != null) {
                //fire notification

                Job currentJob = jobRepository.findById(newMilestone.getJobId()).orElse(null);
                if (currentJob != null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(userName + " created new milestone on " + currentJob.getTitle() + " for your review and approval");
                    body.setSubject("New Milestone");
                    body.setActionType("REDIRECT");
                    body.setAction("/my-job/contract/milestones/" + newMilestone.getJobId() + "/" + newMilestone.getProposalId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setPriority("YES");
                    body.setRecipient(newMilestone.getEmployerId());
                    notificationSender.pushNotification(body);
                }
                //end
                return newMilestone;
            } else {
                logger.info("JOBSERVICE: Unable to add new milestone");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public JobContractExtension approveContractExtension(Principal principal, String request) throws JsonProcessingException {
        JobContractExtension extensionRequest = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContractExtension.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return approveContractExtension(currentUser.getUserFullName(), extensionRequest);
    }

    public JobContractExtension approveContractExtension(String userName, JobContractExtension
            request) {
        try {
            JobContractExtension extension = jobContractExtensionRepository.findContractByProposalAndJobId(request.getProposalId(), request.getJobId());
            ResponseEntity<String> response = null;

            if (extension != null) {
                JobContract contract = jobContractRepository.findContractByProposalAndJobId(request.getProposalId(), request.getJobId());
                if (contract != null) {
                    //set end date from today
                    contract.setEndDate(extension.getDate());
                    contract.setLastModifiedDate(new Date());

                    //start to extend escrow live
                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    response = rest.exchange(baseURL + "/Transaction/reqExtension?appid=" + appId
                            + "&referenceid=" + contract.getContractReference()
                            + "&user_email=" + contract.getUserEmail()
                            + "&reasons=" + extension.getReason()
                            + "&new_date" + extension.getDate().toString()
                            + "&action=" + "accept", HttpMethod.POST, entity, String.class);
                    //done
                    if (response.getStatusCode().is2xxSuccessful())
                        jobContractRepository.save(contract);
                    else
                        logger.info("JOBSERVICE: Escrow transaction failed");
                }

                Optional<JobProposal> proposalData = jobProposalRepository.findById(extension.getProposalId());
                JobProposal proposal = proposalData.orElse(null);
                if (proposal != null) {
                    proposal.setLastModifiedDate(new Date());
                    proposal.setDuration((decodeDuration(proposal.duration, proposal.durationType) + getDifferenceDays(proposal.endDate, extension.getDate())));
                    proposal.setDurationType("D");
                    proposal.setEndDate(extension.getDate());
                }

                if (response != null) {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        extension.setStatus(JobStatus.AC);
                        jobContractExtensionRepository.save(extension);

                        //fire notification
                        Job currentJob = jobRepository.findById(extension.getJobId()).orElse(null);
                        if (proposal != null && currentJob != null) {
                            NotificationBody body = new NotificationBody();
                            body.setBody(userName + "  approved  your request for the delivery date extension as requested");
                            body.setSubject("Contract Extension Approved");
                            body.setActionType("REDIRECT");
                            body.setAction("/job/ongoing/details/" + extension.getJobId());
                            body.setTopic("'Job'");
                            body.setChannel("S");
                            body.setPriority("YES");
                            body.setRecipient(extension.getUserId());
                            notificationSender.pushNotification(body);
                        }
                        //end
                        return extension;
                    } else {
                        logger.info("JOBSERVICE: Escrow transaction failed");
                        return null;
                    }
                } else {
                    logger.info("JOBSERVICE: Escrow transaction failed");
                    return null;
                }

            } else {
                logger.info("JOBSERVICE: Extension request not found");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public JobProjectSubmission submitJob(Principal principal, String
            projectData, MultipartFile[] supportingFiles) throws JsonProcessingException {

        JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(projectData, JobProjectSubmission.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return submitJob(currentUser.getUserFullName(), request, supportingFiles);

    }

    public JobProjectSubmission submitJob(String userName, JobProjectSubmission
            request, MultipartFile[] supportingFiles) {
        try {
            String supporting_file_names = null;
            request.setStatus(JobStatus.PE);

            if (supportingFiles != null)
                supporting_file_names = this.fileStoreService.storeFiles(supportingFiles, request.getProposalId().toString());

            if (supporting_file_names != null)
                request.supportingFiles = supporting_file_names;

            //fire notification
            Job currentJob = jobRepository.findById(request.getJobId()).orElse(null);
            if (currentJob != null) {
                NotificationBody body = new NotificationBody();
                body.setBody(userName + " submitted " + currentJob.getTitle() + " for your review and approval");
                body.setSubject("Project Review");
                body.setActionType("REDIRECT");
                body.setAction("/job/ongoing/details/" + request.getJobId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setPriority("YES");
                body.setRecipient(request.getEmployerId());
                notificationSender.pushNotification(body);
            }
            //end
            return jobProjectSubmissionRepository.save(request);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public APIResponse raiseDispute(Principal principal, String
            projectData, MultipartFile[] attachmentFiles) throws
            JsonProcessingException {
        JobContractDispute request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(projectData, JobContractDispute.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return raiseDispute(currentUser.getUserFullName(), currentUser.getUserId(), request, attachmentFiles);
    }

    public APIResponse raiseDispute(String userName, Long
            userId, JobContractDispute request, MultipartFile[] attachmentFiles) {
        try {
            String attachments = null;
            ResponseEntity<String> response = null;
            String disputeReferenceId =app.makeUIID();
            String transactionReferenceId = "";

            JobContract contract = jobContractRepository.findById(request.getContractId()).orElse(null);
            if (contract != null)
                transactionReferenceId = contract.getContractReference();


            logger.info("The trans id is:" + transactionReferenceId);
            request.setStatus(JobStatus.PE);
            request.setUserId(userId);
            request.setReferenceId(disputeReferenceId);

            if (attachmentFiles != null)
                attachments = this.fileStoreService.storeFiles(attachmentFiles, request.getProposalId().toString());
            if (attachments != null)
                request.attachment = attachments;

            HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
            response = rest.exchange(baseURL + "/Dispute/reportDispute?appid=" + appId
                    + "&referenceid=" + transactionReferenceId
                    + "&dispute_referenceid=" + request.getReferenceId()
                    + "&dispute_category=contract-" + request.getContractId().toString()
                    + "&dispute_description=" + request.getDescription(), HttpMethod.POST, entity, String.class);
            //done
            if (response.getStatusCode().is2xxSuccessful()) {
                //fire notification
                JobContractDispute dispute = jobContractDisputeRepository.save(request);
                if (dispute != null) {
                    Job currentJob = jobRepository.findById(dispute.getJobId()).orElse(null);
                    if (currentJob != null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(userName + " raised a dispute on " + currentJob.getTitle() + "");
                        body.setSubject("Dispute Raised Against you");
                        body.setActionType("REDIRECT");
                        body.setAction("/job/details/" + dispute.getJobId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setPriority("YES");
                        body.setRecipient(dispute.getEmployerId());
                        notificationSender.pushNotification(body);
                    }
                    return new APIResponse("success", true, dispute);

                } else {
                    logger.info("JOBSERV: Unable to raise a dispute, the dispute request is not valid");
                    return new APIResponse("Unable to raise a dispute, the dispute request is not valid", false, null);
                }
            } else {
                logger.info("JOBSERVICE: Escrow transaction failed");
                return new APIResponse("Escrow transaction failed", false, null);
            }
            //end

        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }
    }

    public JobProjectSubmission rejectJob(Principal principal, Long jobId, Long
            requestId) {
        JobProjectSubmission request = jobProjectSubmissionRepository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(JobStatus.RE);
            jobProjectSubmissionRepository.save(request);
            //fire notification
            NotificationBody body = new NotificationBody();
            body.setBody("Job that you submitted has been rejected by the employer");
            body.setSubject("Project Rejected");
            body.setActionType("REDIRECT");
            body.setAction("/job/ongoing/details/" + request.getJobId());
            body.setTopic("'Job'");
            body.setChannel("S");
            body.setPriority("YES");
            body.setRecipient(request.getEmployerId());
            notificationSender.pushNotification(body);
        }
        return request;
    }

    public JobProjectSubmission submitCompletedMilestone(Principal
                                                                 principal, Long milestoneId, String projectData, MultipartFile[] supportingFiles) throws
            JsonProcessingException {
        JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(projectData, JobProjectSubmission.class);
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return submitCompletedMilestone(currentUser.getUserFullName(), milestoneId, request, supportingFiles);
    }


    public JobProjectSubmission submitCompletedMilestone(String
                                                                 userName, Long milestoneId, JobProjectSubmission
                                                                 request, MultipartFile[] supportingFiles) {
        try {
            String supporting_file_names = null;
            JobMilestone milestone = jobMilestoneRepository.findById(milestoneId).orElse(null);
            request.setStatus(JobStatus.PE);

            if (supportingFiles != null)
                supporting_file_names = this.fileStoreService.storeFiles(supportingFiles, request.getProposalId().toString());

            if (supporting_file_names != null) {
                request.supportingFiles = supporting_file_names;
                milestone.supportingFiles = supporting_file_names;
            }

            if (milestone != null) {
                milestone.setStatus(JobStatus.PA);
                jobMilestoneRepository.save(milestone);

                //fire notification
                Job currentJob = jobRepository.findById(request.getJobId()).orElse(null);
                if (currentJob != null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(userName + " submitted milestone for your review and approval");
                    body.setSubject("Milestone Review");
                    body.setActionType("REDIRECT");
                    body.setAction("/my-job/contract/milestones/" + request.getJobId() + "/" + request.getProposalId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setPriority("YES");
                    body.setRecipient(request.getEmployerId());
                    notificationSender.pushNotification(body);
                }
                //end
                return jobProjectSubmissionRepository.save(request);
            } else {
                logger.info("JOBSERVICE: Milestone not found");
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public JobContract endContract(Principal principal, Rate
            rating, Long jobId, Long proposalId, Long userId, int state) {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return endContract(currentUser.getUserFullName(), rating, jobId, proposalId, userId, state);
    }


    public JobContract endContract(String userName, Rate
            rating, Long jobId, Long proposalId, Long userId, int state) {
        try {
            Job job = jobRepository.findById(jobId).orElse(null);
            JobProposal proposal = jobProposalRepository.findProposalByUserId(jobId, userId);
            JobContract contract = jobContractRepository.findContractByProposalAndJobId(proposalId, jobId);
            JobProjectSubmission project = jobProjectSubmissionRepository.findSubmittedProjectByProposalAndUserId(proposalId, userId);

            if (contract != null) {
                contract.setRate(rating.getRate());
                contract.setDescription(rating.getDescription());
                contract.setFeedback(rating.getFeedback());

                if (state == 1) {
                    contract.setEndDate(new Date());
                    contract.setIsSettled(true);
                    contract.setSettlement(contract.getContractReference());
                    contract.setStatus(JobStatus.CO);
                    proposal.setStatus(JobStatus.CO);


                    //start to release escrow amount to freelancer
                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/release?appid=" + appId
                            + "&referenceid=" + contract.getContractReference()
                            + "&user_email=" + contract.getUserEmail()
                            + "&reasons=Job Completed", HttpMethod.POST, entity, String.class);
                    //done

                    if (response.getStatusCode().is2xxSuccessful()) {

                        if (rating != null) {
                            Rate rate = jobRateRepository.findByUserId(proposal.getUserId());
                            if (rate != null) {
                                rate.setRate((rate.getRate() + rating.getRate()));
                                rate.setFeedback(rating.getFeedback());
                                rate.setDescription(rating.getDescription());
                                rate.setLastModifiedDate(new Date());
                                jobRateRepository.save(rate);
                            } else {
                                rating.setLastModifiedDate(new Date());
                                rating.setUserId(proposal.getUserId());
                                jobRateRepository.save(rating);
                            }
                        }
                        if (job != null) {
                            job.setStatus(JobStatus.CO);
                            jobRepository.save(job);
                        }
                        if (proposal != null) {
                            proposal.setStatus(JobStatus.CO);
                            proposal.setEndDate(new Date());
                            jobProposalRepository.save(proposal);
                        }
                        if (project != null) {
                            project.setStatus(JobStatus.CO);
                            jobProjectSubmissionRepository.save(project);
                        }
                        jobContractRepository.save(contract);
                        //fire notification
                        Job currentJob = jobRepository.findById(jobId).orElse(null);
                        if (currentJob != null) {
                            NotificationBody body = new NotificationBody();
                            body.setBody(userName + " ended your contract and release the sum of " + proposal.getBidAmount() + " to your bank account");
                            body.setSubject("Contract Ended");
                            body.setActionType("REDIRECT");
                            body.setAction("/job/ongoing/details/" + jobId);
                            body.setTopic("'Job'");
                            body.setChannel("S");
                            body.setPriority("YES");
                            body.setRecipient(proposal.getUserId());
                            notificationSender.pushNotification(body);
                        }
                        //end
                        return contract;
                    } else {
                        logger.info("JOBSERV: Escrow transaction Failed");
                        return null;
                    }
                } else {
                    contract.setEndDate(new Date());
                    contract.setIsSettled(false);
                    contract.setFeedback(String.valueOf(state));
                    contract.setSettlement("Contract ended without settlement");
                    contract.setDescription("Employer ended the contract");
                    contract.setStatus(JobStatus.RE);


                    if (job != null) {
                        job.setStatus(JobStatus.IA);
                        jobRepository.save(job);
                    }
                    if (project != null) {
                        project.setStatus(JobStatus.RE);
                        jobProjectSubmissionRepository.save(project);
                    }
                    if (proposal != null) {
                        proposal.setStatus(JobStatus.RE);
                        proposal.setEndDate(new Date());
                        jobProposalRepository.save(proposal);
                    }
                    jobContractRepository.save(contract);
                    //fire notification
                    Job currentJob = jobRepository.findById(jobId).orElse(null);
                    if (currentJob != null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(userName + " ended your contract for " + currentJob.getTitle() + ", you are not find with it? you can raise dispute.");
                        body.setSubject("Contract Ended");
                        body.setActionType("REDIRECT");
                        body.setAction("/job/details/" + jobId);
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(proposal.getUserId());
                        notificationSender.pushNotification(body);
                    }
                    //end
                    return contract;
                }
            } else {
                logger.info("JOBSERVICE: Contract not found");
                return null;
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    public APIResponse modifyMilestoneState(Principal
                                                    principal, Long milestoneId, String newStatus) {

        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        return modifyMilestoneState(currentUser, milestoneId, JobStatus.valueOf(newStatus));

    }

    public APIResponse modifyMilestoneState(JwtUserDetail
                                                    currentUser, Long milestoneId, JobStatus
                                                    newStatus) {

        try {
            JobMilestone milestone = jobMilestoneRepository.findById(milestoneId).orElse(null);
            int status;
            String remark;

            if (milestone != null) {
                milestone.setStatus(newStatus);
                if (newStatus == JobStatus.AC) {
                    milestone.setStartDate(new Date());
                    JobProposal proposal = jobProposalRepository.findById(milestone.getProposalId()).orElse(null);
                    Job job = jobRepository.findById(proposal.getJobId()).orElse(null);
                    if (proposal != null) {
                        proposal.setStatus(JobStatus.WP);
                        //send milestone amount to escrow
                        JobContract contract = jobContractRepository.findById(proposal.getContractId()).orElse(null);
                        if (contract != null) {

                            String paymentReferenceId = app.makeUIID();
                            milestone.setInitialPaymentReferenceA(paymentReferenceId);
                            milestone.setStatus(JobStatus.WP);
                            milestone.setContractId(contract.getId());
                            milestone.setContractReference(contract.getContractReference());

                            contract.setInitialPaymentReferenceA(paymentReferenceId);
                            contract.setLastModifiedDate(new Date());
                                                        Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(Calendar.DATE, proposal.duration.intValue());
                            milestone.setEndDate(c.getTime());
                            milestone.setStartDate(new Date());

                            PaymentRequest payment = new PaymentRequest();
                            payment.setProposalId(contract.getProposalId());
                            payment.setAmount(Integer.parseInt(milestone.getAmount().toString()));
                            payment.setNarration("Transfer from Employer to Escrow account for Milestone " + milestone.getTitle());
                            payment.setCreditAccountName(escrowAccountName);
                            payment.setCreditAccountNumber(escrowAccountNumber);
                            payment.setDebitAccountName(proposal.getAccountName());
                            payment.setDebitAccountNumber(proposal.getAccountNumber());
                            payment.setPaymentReference(paymentReferenceId);
                            payment.setExecutedFor("Job:"+job.getId());
                            payment.setExecutedBy(currentUser.getUserUUID());

                            APIResponse paymentResponse=jobPaymentService.makePayment(payment);
                            if (paymentResponse.isSuccess()) {
                                milestone.setInitialPaymentReferenceB(paymentResponse.getPayload().toString());
                                contract.setInitialPaymentReferenceB(paymentResponse.getPayload().toString());
                                //set milestone amount to the escrow
                                HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                                ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/create?appid=" + appId
                                        + "&referenceid=" + milestone.getMilestoneReference()
                                        + "&user_email=" + contract.getUserEmail()
                                        + "&amount=" + milestone.getAmount()
                                        + "&country=" + contract.getCountry()
                                        + "&currency=" + contract.getCurrency()
                                        + "&customer_email=" + contract.getFreelancerEmailAddress()
                                        + "&merchant_email=" + contract.getEmployerEmailAddress()
                                        + "&customer_account_number=" + contract.getFreelancerAccountNumber()
                                        + "&merchant_account_number=" + contract.getEmployerAccountNumber()
                                        + "&customer_bank_code=032"
                                        + "&merchant_bank_code=032"
                                        + "&customer_name=" + contract.getFreelancerAccountName()
                                        + "&merchant_name=" + contract.getEmployerAccountName()
                                        + "&customer_phone=" + contract.getFreelancerPhoneNumber()
                                        + "&merchant_phone=" + contract.getEmployerPhoneNumber()
                                        + "&peppfees=" + contract.getPeppfees()
                                        + "&startdate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(milestone.getStartDate())
                                        + "&enddate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(milestone.getEndDate())
                                        + "&transfer_reference_id=" + milestone.getMilestoneReference(), HttpMethod.POST, entity, String.class);
                                if (response.getStatusCode().is2xxSuccessful()) {
                                    jobRepository.save(job);
                                    jobProposalRepository.save(proposal);
                                    jobContractRepository.save(contract);
                                    status = 1;
                                    remark = "Escrow transaction succeeded";
                                    logger.info("JOBSERVICE: Escrow transaction succeeded");
                                } else {
                                    status = 0;
                                    remark = "Escrow transaction failed";
                                    logger.info("JOBSERVICE: Escrow transaction failed");
                                }
                                //done
                            } else {
                                status = 0;
                                remark = paymentResponse.getMessage();
                            }
                        } else {
                            status = 0;
                            remark = "Contract not found";
                            logger.error("JOBSERVICE: Contract not found");
                        }
                    } else {
                        status = 0;
                        remark = "Proposal not found";
                        logger.error("JOBSERVICE: Proposal not found");
                    }
                } else {
                    status = 0;
                    remark = "Milestone status mismatch";
                    logger.error("JOBSERVICE: Milestone status mismatch");
                }

                if (status == 1) {
                    //fire notification
                    Job currentJob = jobRepository.findById(milestone.getJobId()).orElse(null);
                    if (currentJob != null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(currentUser.getUserFullName() + " approved the milestone you submitted for " + currentJob.getTitle() + ", you can proceed to start working on it");
                        body.setSubject("Milestone Approval");
                        body.setActionType("REDIRECT");
                        body.setAction("/my-job/contract/milestones/" + milestone.getJobId() + "/" + milestone.getProposalId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(milestone.getUserId());
                        notificationSender.pushNotification(body);
                    }
                    //end
                    JobMilestone savedMilestone = jobMilestoneRepository.save(milestone);
                    return new APIResponse("success", true, savedMilestone);
                } else {
                    logger.info("JOBSERVICE: Transaction failed");
                    return new APIResponse(remark, false, null);
                }
            } else {
                logger.info("JOBSERVICE: Milestone request not found");
                remark = "Milestone request not found";
                return new APIResponse(remark, false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }
    }

    public APIResponse approveCompletedMilestone
            (Principal principal, Long milestoneId, String requestData) throws
            JsonProcessingException {

        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(requestData, JobProjectSubmission.class);
        return approveCompletedMilestone(currentUser.getUserFullName(), milestoneId, request);
    }

    public APIResponse approveCompletedMilestone
            (String userName, Long
                    milestoneId, JobProjectSubmission request) {
        try {
            JobMilestone milestone = jobMilestoneRepository.findById(milestoneId).orElse(null);
            JobContract contract = jobContractRepository.findContractByProposalAndJobId(milestone.getProposalId(), milestone.getJobId());

            if (milestone != null) {
                milestone.setStatus(JobStatus.CO);
                contract.setClearedAmount((contract.getClearedAmount() + milestone.getAmount().intValue()));

                jobMilestoneRepository.save(milestone);
                jobContractRepository.save(contract);

                //start to release escrow amount to freelancer
                HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/release?appid=" + appId
                        + "&referenceid=" + milestone.getMilestoneReference()
                        + "&user_email=" + contract.getUserEmail()
                        + "&reasons=Milestone Completed", HttpMethod.POST, entity, String.class);
                //done
                if (response.getStatusCode().is2xxSuccessful()) {
                    //fire notification
                    Job currentJob = jobRepository.findById(request.getJobId()).orElse(null);
                    if (currentJob != null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(userName + " approved the milestone you submitted for " + currentJob.getTitle() + ", and the sum of " + milestone.getAmount().toString() + " has been released to your account");
                        body.setSubject("Milestone Completed");
                        body.setActionType("REDIRECT");
                        body.setAction("/my-job/contract/milestones/" + request.getJobId() + "/" + request.getProposalId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(request.getUserId());
                        notificationSender.pushNotification(body);
                    }
                    //end
                    return new APIResponse("success", true, milestone);
                } else {
                    logger.info("JOBSERVICE: Escrow transaction failed");
                    return new APIResponse("Escrow transaction failed", false, null);
                }
            } else {
                logger.info("JOBSERVICE: Milestone request not found");
                return new APIResponse("Milestone not found", false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("Oops! An Error Occurred", false, null);
        }
    }

    public APIResponse settleContractPaymentById(Principal principal, String contractReferenceId) throws JsonProcessingException {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        JobContract contract;
        JobMilestone milestone;

        contract= jobContractRepository.findByContractReference(contractReferenceId).orElse(null);
        milestone= jobMilestoneRepository.findMilestoneByMilestoneReference(contractReferenceId).orElse(null);
        if (contract == null) {
            if (milestone == null)
                return new APIResponse("Contract not found with from Reference Id Provided", false, null);
            else {
                contract=jobContractRepository.findById(milestone.getContractId()).orElse(null);
            }
        }

        if(contract.getStatus().equals(JobStatus.WP)) {
            //check if contract is available
            JobProposal proposal = jobProposalRepository.findById(contract.getProposalId()).orElse(null);
            if (proposal == null)
                return new APIResponse("No Proposal found for this Contract", false, null);
            //check if job is available
            Job job = jobRepository.findById(contract.getJobId()).orElse(null);
            if (job == null)
                return new APIResponse("No Job history found for this Contract", false, null);


            int depositedAmount;
            if(contract.getWorkMethod()=="Overall") {
                depositedAmount = contract.getAmount();
            }
            else{
                if(milestone==null)
                    return new APIResponse("No Milestone found with the reference provided", false, null);
                else
                    depositedAmount= milestone.getAmount().intValue();
            }

           if(depositedAmount>0) {
               int UBNIncomeCharge = 400;
               int pepperestIncomeCharge = 100;
               int freelancerIncomeAmount = Math.round((depositedAmount - pepperestIncomeCharge) - UBNIncomeCharge);


               ArrayList<JobBulkPayment> bulkPaymentStack = new ArrayList<>();
               String paymentReference = app.makeUIID();
               String narration = "Settlement from Kula for " + job.getTitle();

               //Bulk transfers for settlement
               JobBulkPayment escrowAccount = new JobBulkPayment();
               escrowAccount.setAccountNumber(escrowAccountNumber);
               escrowAccount.setAccountName(escrowAccountName);
               escrowAccount.setNarration(narration);
               escrowAccount.setExecutedBy(currentUser.getUserUUID());
               escrowAccount.setExecutedFor("Contract:" + contract.getContractReference());
               escrowAccount.setPaymentReference(paymentReference);
               escrowAccount.setCrDrFlag("D");
               escrowAccount.setAccountType("GL");
               escrowAccount.setTransactionId("1");
               escrowAccount.setAmount(0);


               //***1 Level***
               JobBulkPayment UBNAccount = new JobBulkPayment();
               //###############
               escrowAccount.setAmount(UBNIncomeCharge);
               escrowAccount.setTransactionId("1");
               //##############
               UBNAccount.setTransactionId("2");
               UBNAccount.setAccountName(UBNIncomeAccountName);
               UBNAccount.setAccountNumber(UBNIncomeAccountNumber);
               UBNAccount.setNarration(narration);
               UBNAccount.setExecutedBy(currentUser.getUserUUID());
               UBNAccount.setExecutedFor("contract:" + contract.getContractReference());
               UBNAccount.setPaymentReference(paymentReference);
               UBNAccount.setCrDrFlag("C");
               UBNAccount.setAccountType("GL");
               UBNAccount.setAmount(UBNIncomeCharge);
               //############# add to bulk transfer stack ###########
               bulkPaymentStack.add(escrowAccount);
               bulkPaymentStack.add(UBNAccount);


               //***2 Level***
               JobBulkPayment pepperestAccount = new JobBulkPayment();
               //##############
               escrowAccount.setAmount(pepperestIncomeCharge);
               escrowAccount.setTransactionId("3");
               //#############
               pepperestAccount.setTransactionId("4");
               pepperestAccount.setAccountName(UBNIncomeAccountName);
               pepperestAccount.setAccountNumber(UBNIncomeAccountNumber);
               pepperestAccount.setNarration(narration);
               pepperestAccount.setExecutedBy(currentUser.getUserUUID());
               pepperestAccount.setExecutedFor("contract:" + contract.getContractReference());
               pepperestAccount.setPaymentReference(paymentReference);
               pepperestAccount.setCrDrFlag("C");
               pepperestAccount.setAccountType("GL");
               pepperestAccount.setAmount(pepperestIncomeCharge);
               //############# add to bulk transfer stack ###########
               bulkPaymentStack.add(escrowAccount);
               bulkPaymentStack.add(pepperestAccount);


               //***3 Level***
               JobBulkPayment freelancerAccount = new JobBulkPayment();
               //##################
               escrowAccount.setAmount(freelancerIncomeAmount);
               escrowAccount.setTransactionId("5");//update escrow account information with the payment for UBN
               //##################
               freelancerAccount.setTransactionId("6");
               freelancerAccount.setAccountName(UBNIncomeAccountName);
               freelancerAccount.setAccountNumber(UBNIncomeAccountNumber);
               freelancerAccount.setNarration(narration);
               freelancerAccount.setExecutedBy(currentUser.getUserUUID());
               freelancerAccount.setExecutedFor("contract:" + contract.getContractReference());
               freelancerAccount.setPaymentReference(paymentReference);
               freelancerAccount.setCrDrFlag("C");
               freelancerAccount.setAccountType("CASA");
               freelancerAccount.setAmount(freelancerIncomeAmount);
               //############# add to bulk transfer stack ###########
               bulkPaymentStack.add(escrowAccount);
               bulkPaymentStack.add(freelancerAccount);

               app.print(bulkPaymentStack);
               APIResponse apiResponse = jobPaymentService.makeBulkPayment(bulkPaymentStack);
               if (apiResponse.isSuccess()) {


                   //update job
                   if (contract.getWorkMethod() == "Overall") {
                       contract.setIsSettled(true);
                       contract.setSettlementPaymentReferenceB(apiResponse.getPayload().toString());
                       contract.setEndDate(new Date());
                       contract.setLastModifiedDate(new Date());
                       proposal.setEndDate(new Date());
                       proposal.setStatus(JobStatus.CO);
                       contract.setStatus(JobStatus.CO);
                       job.setStatus(JobStatus.CO);
                   }else{
                       contract.setSettlementPaymentReferenceB(apiResponse.getPayload().toString());
                       contract.setLastModifiedDate(new Date());
                   }
                   job.setLastModifiedDate(new Date());

                   jobRepository.save(job);
                   jobProposalRepository.save(proposal);
                   jobContractRepository.save(contract);

                   return new APIResponse("Request Successful", true, apiResponse.getPayload());
               } else {
                   return apiResponse;
               }

           }
           else{
               return new APIResponse("Contract amount can't be ZERO", false, null);
           }

        }else{
            return new APIResponse("This Contract is not active, it might have  been settled already", false, null);
        }
    }


    public APIResponse reverseContractPaymentById(Principal principal, String contractReferenceId) throws JsonProcessingException {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        //check if contract exist
        JobContract contract;
        JobMilestone milestone;

        contract= jobContractRepository.findByContractReference(contractReferenceId).orElse(null);
        milestone= jobMilestoneRepository.findMilestoneByMilestoneReference(contractReferenceId).orElse(null);
        if (contract == null) {
            if (milestone == null)
                return new APIResponse("Contract not found with from Reference Id Provided", false, null);
            else {
                contract=jobContractRepository.findById(milestone.getContractId()).orElse(null);
            }
        }

        if (contract.getStatus().equals(JobStatus.WP)) {
            //check if proposal is available
            JobProposal proposal = jobProposalRepository.findById(contract.getProposalId()).orElse(null);
            if (proposal == null)
                return new APIResponse("Proposal not found for the Contract", false, null);

            //check if job is available
            Job job = jobRepository.findById(contract.getJobId()).orElse(null);
            if (job == null)
                return new APIResponse("Job not found for the Contract", false, null);


            PaymentRequest payment = new PaymentRequest();
            int depositedAmount;
            if(contract.getWorkMethod()=="Overall"){
                    depositedAmount=contract.getAmount();
                    payment.setExecutedFor("Contract:"+contractReferenceId);
            }else{

                 if(milestone==null)
                    return new APIResponse("No Milestone found with the reference provided", false, null);
                else {
                    depositedAmount = milestone.getAmount().intValue();
                    payment.setExecutedFor("Milestone:"+contractReferenceId);
                }
            }

            if(depositedAmount>0) {

                String paymentReference = app.makeUIID();
                //reverse payment to employer
                payment.setProposalId(contract.getProposalId());
                payment.setAmount(depositedAmount);
                payment.setNarration("Reversal from Kula for " + job.getTitle());
                payment.setDebitAccountName(escrowAccountName);
                payment.setDebitAccountNumber(escrowAccountNumber);
                payment.setCreditAccountName(contract.getEmployerAccountName());
                payment.setCreditAccountNumber(contract.getEmployerAccountNumber());
                payment.setPaymentReference(paymentReference);
                payment.setExecutedBy(currentUser.getUserUUID());
                //transfer back the charged amount to the Employer
                APIResponse transferResponse = jobPaymentService.makePayment(payment);
                if (transferResponse.isSuccess()) {
                    //update the contract and deactivate contract when reversal is done
                    contract.setReversalPaymentReferenceB(transferResponse.getPayload().toString());
                    if (milestone != null) {
                        milestone.setReversalPaymentReferenceB(transferResponse.getPayload().toString());
                        milestone.setStatus(JobStatus.IA);
                        milestone.setEndDate(new Date());
                    } else {
                        contract.setStatus(JobStatus.IA);
                        contract.setLastModifiedDate(new Date());

                    }
                    jobMilestoneRepository.save(milestone);
                    jobContractRepository.save(contract);
                    logger.info("JOBSERVICE: Payment successful");
                    return new APIResponse("Reversal Successful", true, transferResponse.getPayload());
                } else {
                    return new APIResponse(transferResponse.getMessage(), false, null);
                }
            }else{
                return new APIResponse("Contract amount can't be ZERO", false, null);
            }
        } else {
            return new APIResponse("This Contract is not active, it  might have  been settled already", false, null);
        }
    }
}

