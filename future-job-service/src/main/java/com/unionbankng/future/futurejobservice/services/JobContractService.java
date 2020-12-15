package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.*;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.pojos.User;
import com.unionbankng.future.futurejobservice.repositories.*;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JobContractService implements Serializable {


    @Value("${sidekiq.escrow.appId}")
    private int  appId;
    @Value("${sidekiq.escrow.token}")
    private String token;
    @Value("${sidekiq.accountName}")
    private String sidekiqAccountName;
    @Value("${sidekiq.accountNumber}")
    private String sidekiqAccountNumber;
    private String baseURL="https://pepperest.com/EscrowService/api/Escrow";
    private Logger logger = LoggerFactory.getLogger(JobContractService.class);


    @Autowired
    private RestTemplate rest;
    private final JobRepository jobRepository;
    private final JobContractRepository jobContractRepository;
    private final JobContractExtensionRepository jobContractExtensionRepository;
    private final JobProposalRepository jobProposalRepository;
    private final JobNotificationRepository jobNotificationRepository;
    private  final JobProjectSubmissionRepository jobProjectSubmissionRepository;
    private final  JobMilestoneRepository jobMilestoneRepository;
    private  final FileStoreService fileStoreService;
    private final BankTransferService bankTransferService;
    private  final  JobTeamDetailsRepository jobTeamDetailsRepository;
    private  final NotificationSender notificationSender;
    private final  UserService userService;



    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setCacheControl("no-cache");
        headers.add("Token",token);
        return  headers;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long decodeDuration(long duration, String type) {
         long []factor = {1, 7, 30, 360};
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
    public JobContract findJobContractByProposalAndJobId(Long proposalId, Long jobId){
        return  jobContractRepository.findContractByProposalAndJobId(proposalId,jobId);
    }
    public JobContractExtension findContractExtensionByProposalId(Long proposalId, Long userId){
        return  jobContractExtensionRepository.findContractByProposalAndUserId(proposalId,userId);
    }
    public JobProjectSubmission findJobSubmittedByProposalId(Long proposalId, Long userId){
        return  jobProjectSubmissionRepository.findSubmittedProjectByProposalAndUserId(proposalId,userId);
    }

    public JobMilestone findContractMilestoneByProposalId(Long proposalId, Long userId){
        return  jobMilestoneRepository.findMilestoneByProposalAndJobId(proposalId,userId);
    }
    public JobMilestone findMilestoneById(Long milestoneId){
        return  jobMilestoneRepository.findById(milestoneId).orElse(null);
    }
    public JobMilestone findContractMilestoneByJobId(Long proposalId, Long jobId){
        return  jobMilestoneRepository.findMilestoneByProposalAndJobId(proposalId,jobId);
    }

    public List<JobMilestone> findAllContractMilestoneByProposalJobIdAndStatus(Long proposalId, Long jobId, String status){
        return  jobMilestoneRepository.findAllMilestonesByProposalJobAndStatus(proposalId,jobId,status);
    }
    public List<JobMilestone> findAllContractMilestoneByProposalJobId(Long proposalId, Long jobId){
        return  jobMilestoneRepository.findAllMilestonesByProposalAndJobId(proposalId,jobId);
    }
    public JobContract approveJobProposal(OAuth2Authentication authentication,String  request, Model model) throws JsonProcessingException {
        try {
            JobContract contract = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContract.class);
            JobProposal proposal= jobProposalRepository.findById(contract.getProposalId()).orElse(null);
            Job job = jobRepository.findById(proposal.getJobId()).orElse(null);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

            UUID referenceId = UUID.randomUUID();
            String transferReferenceId=referenceId.toString().replaceAll("-","");
            int peppFess = (int) (2.5 / 100) * contract.getAmount() + 200;
            contract.setPeppfees(peppFess);
            contract.setReferenceId(referenceId.toString());
            contract.setTransferReferenceId(transferReferenceId);
            contract.setAppId(appId);
            int status=0;

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, proposal.duration.intValue());
            contract.setEndDate(c.getTime());
            contract.setStartDate(new Date());
            proposal.setStatus(JobProposalStatus.WP);
            proposal.setLastModifiedDate(new Date());
            proposal.setStartDate(new Date());
            proposal.setEndDate(c.getTime());

            if(contract.getWorkMethod()=="Milestone"){
                status=1;
                //fire notification
                    if(job!=null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(currentUser.getUserFullName() + " approved your contract and the amount will be paid to you base on the milestone you complete");
                    body.setSubject("Proposal Approval");
                    body.setActionType("REDIRECT");
                    body.setAction("/job/ongoing/details/"+proposal.getJobId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setRecipient(proposal.getUserId());
                    notificationSender.pushNotification(body);
                }
                //end
            }else {
                //transfer the amount to Sidekiq main account
                JobTransfer transfer=new JobTransfer();
                transfer.setUserId(proposal.getUserId());
                transfer.setJobId(proposal.getJobId());
                transfer.setProposalId(proposal.getId());
                transfer.setEmployerId(proposal.getEmployerId());
                transfer.setCreatedAt(new Date());
                //transfer
                transfer.setAmount(contract.getAmount());
                transfer.setCurrency("NGN");
                transfer.setPaymentReference(contract.getTransferReferenceId());
                transfer.setInitBranchCode("682");
                //credit
                transfer.setCreditAccountName(sidekiqAccountName);
                transfer.setCreditAccountNumber(sidekiqAccountNumber);
                transfer.setCreditAccountBankCode("032");
                transfer.setCreditAccountBranchCode("682");
                transfer.setCreditAccountType("CASA");
                transfer.setCreditNarration(job.getTitle());
                //debit
                transfer.setDebitAccountName(proposal.getAccountName());
                transfer.setDebitAccountNumber(proposal.getAccountNumber());
                transfer.setDebitAccountBranchCode(proposal.getBranchCode());
                transfer.setDebitAccountBranchCode("682");
                transfer.setDebitAccountType("CASA");
                transfer.setDebitNarration(job.getTitle());


                logger.info(new ObjectMapper().writeValueAsString(transfer));
                UBNFundsTransferResponse transferResponse= bankTransferService.transferUBNtoUBN(transfer);

                if(transferResponse.getCode().compareTo("00")==0) {

                   if(job!=null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody("Your payment of "+contract.getAmount()+" has been successful");
                        body.setSubject("Payment Successful");
                        body.setActionType("REDIRECT");
                        body.setAction("/job/ongoing/details/"+proposal.getJobId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(proposal.getEmployerId());
                        notificationSender.pushNotification(body);
                    }

                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/create?appid=" + appId
                            + "&referenceid=" + contract.getReferenceId()
                            + "&user_email=" + contract.getUserEmail()
                            + "&amount=" + contract.getAmount()
                            + "&country=" + contract.getCountry()
                            + "&currency=" + contract.getCurrency()
                            + "&customer_email=" + contract.getUserEmail()
                            + "&merchant_email=" + contract.getMerchantEmail()
                            + "&customer_account_number=" + contract.getCustomerAccountNumber()
                            + "&merchant_account_number=" + contract.getMerchantAccountNumber()
                            + "&customer_bank_code=" + contract.getCustomerBankCode()
                            + "&merchant_bank_code=" + contract.getMerchantAccountNumber()
                            + "&customer_name=" + contract.getCustomerName()
                            + "&merchant_name=" + contract.getMerchantName()
                            + "&customer_phone=" + contract.getCustomerPhone()
                            + "&merchant_phone=" + contract.getMerchantPhone()
                            + "&peppfees=" + contract.getPeppfees()
                            + "&startdate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(contract.getStartDate())
                            + "&enddate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(contract.getEndDate())
                            + "&transfer_reference_id=" + contract.getTransferReferenceId(), HttpMethod.POST, entity, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        status = 1;

                        //fire notifications
                        NotificationBody body1 = new NotificationBody();
                        body1.setBody("Your " + contract.getAmount() +" deducted is currently in our Escrow, it will only be released to the freelancer when you confirm that the job done is okay by you.");
                        body1.setSubject("Contract Amount is in Escrow");
                        body1.setActionType("REDIRECT");
                        body1.setAction("/job/ongoing/details/" + proposal.getJobId());
                        body1.setTopic("'Job'");
                        body1.setChannel("S");
                        body1.setRecipient(proposal.getEmployerId());
                        notificationSender.pushNotification(body1);

                        NotificationBody body2 = new NotificationBody();
                        body2.setBody(currentUser.getUserFullName()+ " approved your contract and credited our escrow with the sum of " + proposal.getBidAmount());
                        body2.setSubject("Proposal Approval");
                        body2.setActionType("REDIRECT");
                        body2.setAction("/job/ongoing/details/" + proposal.getJobId());
                        body2.setTopic("'Job'");
                        body2.setChannel("S");
                        body2.setRecipient(proposal.getUserId());
                        notificationSender.pushNotification(body2);

                        //end
                    } else {
                        logger.info("JOBSERVICE: Escrow transaction failed");
                        logger.error("JOBSERVICE: Escrow "+response.getBody());
                        status = 0;
                    }
                }else{
                    logger.info("JOBSERVICE: Payment failed");
                    logger.error(transferResponse.getMessage());
                    logger.error(transferResponse.getCode());
                    status = 0;
                }
            }
            if(status==1){
                 Optional<Job> jobData =jobRepository.findById(contract.getJobId());
                 if(job!=null) {
                     job.setStatus(JobStatus.WP);
                     job.setLastModifiedDate(new Date());
                     jobRepository.save(job);

                     if(job.getType()==JobType.TEAMS_PROJECT) {

                         JobTeamDetails existingMember=jobTeamDetailsRepository.findByProposalId(proposal.getId());
                         if(existingMember==null){
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
                             teamMember.setStatus(JobTeamStatus.AC);
                             teamMember.setDescription(proposal.about);
                             teamMember.setPercentage(Long.valueOf(10));
                             teamMember.setAmount(proposal.bidAmount);
                             teamMember.setStartDate(new Date());
                             teamMember.setEndDate(c.getTime());
                             jobTeamDetailsRepository.save(teamMember);
                         }else{
                             existingMember.setStartDate(new Date());
                             existingMember.setEndDate(c.getTime());
                             existingMember.setStatus(JobTeamStatus.AC);
                             jobTeamDetailsRepository.save(existingMember);
                         }
                     }
                 }else{
                     logger.info("JOBSERVICE: Job not found");
                 }
                JobContract savedContract= jobContractRepository.save(contract);
                proposal.setContractId(savedContract.getId());
                jobProposalRepository.save(proposal);
                return savedContract;
            }
            else {
                logger.info("JOBSERVICE: Transaction failed");
                return null;
            }

        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }
    public  JobContractExtension requestContractExtension(OAuth2Authentication authentication,String request) throws JsonProcessingException {
        try {
            JobContractExtension extensionRequest = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContractExtension.class);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            extensionRequest.setStatus(JobExtensionStatus.PE);
            JobContractExtension extension = jobContractExtensionRepository.save(extensionRequest);
            if (extension != null) {

                //fire notification
                Job currentJob=jobRepository.findById(extension.getJobId()).orElse(null);
                if(currentJob!=null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(currentUser.getUserFullName()+" want you to help extend delivery date for "+currentJob.getTitle()+" to " + extension.getDate().toString());
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
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public  JobMilestone addNewMilestone(OAuth2Authentication authentication,String request) throws JsonProcessingException {
        try {
            JobMilestone milestone = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobMilestone.class);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            milestone.setStatus(JobMilestoneStatus.PE);
            JobMilestone newMilestone = jobMilestoneRepository.save(milestone);
            if (newMilestone != null) {
                //fire notification

                Job currentJob=jobRepository.findById(newMilestone.getJobId()).orElse(null);
                if(currentJob!=null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(currentUser.getUserFullName() + " created new milestone on "+currentJob.getTitle()+" for your review and approval");
                    body.setSubject("New Milestone");
                    body.setActionType("REDIRECT");
                    body.setAction("/my-job/contract/milestones/"+newMilestone.getJobId()+"/"+newMilestone.getProposalId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setRecipient(newMilestone.getEmployerId());
                    notificationSender.pushNotification(body);
                }
                //end

                return newMilestone;
            } else {
                logger.info("JOBSERVICE: Unable to add new milestone");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

    public  JobContractExtension approveContractExtension(OAuth2Authentication authentication,String request) throws JsonProcessingException {
        try {
            JobContractExtension extensionRequest = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(request, JobContractExtension.class);
            JobContractExtension extension = jobContractExtensionRepository.findContractByProposalAndJobId(extensionRequest.getProposalId(), extensionRequest.getJobId());
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            ResponseEntity<String> response = null;

            if (extension != null) {
                JobContract contract = jobContractRepository.findContractByProposalAndJobId(extensionRequest.getProposalId(), extensionRequest.getJobId());
                if (contract != null) {
                    //set end date from today
                    contract.setEndDate(extension.getDate());
                    contract.setLastModifiedDate(new Date());

                    //start to extend escrow live
                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    response = rest.exchange(baseURL + "/Transaction/reqExtension?appid=" + appId
                            + "&referenceid=" + contract.getReferenceId()
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

                Optional<JobProposal> proposalData=jobProposalRepository.findById(extension.getProposalId());
                JobProposal proposal=proposalData.orElse(null);
                if(proposal!=null) {
                        proposal.setLastModifiedDate(new Date());
                        proposal.setDuration((decodeDuration(proposal.duration, proposal.durationType)+getDifferenceDays(proposal.endDate,extension.getDate())));
                        proposal.setDurationType("D");
                        proposal.setEndDate(extension.getDate());
                }

                if(response!=null) {
                    if(response.getStatusCode().is2xxSuccessful()) {
                        extension.setStatus(JobExtensionStatus.AC);
                        jobContractExtensionRepository.save(extension);

                        //fire notification 
                        Job currentJob=jobRepository.findById(extension.getJobId()).orElse(null);
                        if(proposal!=null && currentJob!=null) {
                            NotificationBody body = new NotificationBody();
                            body.setBody(currentUser.getUserFullName()+"  approved  your request for the delivery date extension as requested");
                            body.setSubject("Contract Extension Approved");
                            body.setActionType("REDIRECT");
                            body.setAction("/job/ongoing/details/" + extension.getJobId());
                            body.setTopic("'Job'");
                            body.setChannel("S");
                            body.setRecipient(extension.getUserId());
                            notificationSender.pushNotification(body);
                        }
                        //end


                        return extension;
                    }else{
                        logger.info("JOBSERVICE: Escrow transaction failed");
                        return  null;
                    }
                }else{
                    logger.info("JOBSERVICE: Escrow transaction failed");
                    return  null;
                }

            } else {
                logger.info("JOBSERVICE: Extension request not found");
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }

    public JobProjectSubmission submitJob(OAuth2Authentication authentication,String projectData, MultipartFile[] supportingFiles) {
        try {
            String supporting_file_names = null;
            JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(projectData, JobProjectSubmission.class);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            request.setStatus(JobSubmissionStatus.PE);

            if (supportingFiles!=null)
                supporting_file_names = this.fileStoreService.storeFiles(supportingFiles, request.getProposalId());

            if (supporting_file_names != null)
                request.supportingFiles = supporting_file_names;

            //fire notification 
            Job currentJob=jobRepository.findById(request.getJobId()).orElse(null);
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody(currentUser.getUserFullName()+ " submitted "+currentJob.getTitle()+" for your review and approval");
                body.setSubject("Project Review");
                body.setActionType("REDIRECT");
                body.setAction("/job/ongoing/details/" + request.getJobId());
                body.setTopic("'Job'");
                body.setChannel("S");
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
    public JobProjectSubmission rejectJob(OAuth2Authentication authentication,Long jobId, Long requestId) {
        JobProjectSubmission request = jobProjectSubmissionRepository.findById(requestId).orElse(null);
        if(request !=null) {
            request.setStatus(JobSubmissionStatus.RE);
            jobProjectSubmissionRepository.save(request);
            //fire notification
            NotificationBody body = new NotificationBody();
            body.setBody("Job that you submitted has been rejected by the employer");
            body.setSubject("Project Rejected");
            body.setActionType("REDIRECT");
            body.setAction("/job/ongoing/details/" + request.getJobId());
            body.setTopic("'Job'");
            body.setChannel("S");
            body.setRecipient(request.getEmployerId());
            notificationSender.pushNotification(body);
        }
        return request;
    }

    public JobProjectSubmission submitCompletedMilestone(OAuth2Authentication authentication,Long id, String projectData, MultipartFile[] supportingFiles) {
        try {
            String supporting_file_names = null;
            JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(projectData, JobProjectSubmission.class);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            request.setStatus(JobSubmissionStatus.PE);

            if (supportingFiles!=null)
                supporting_file_names = this.fileStoreService.storeFiles(supportingFiles, request.getProposalId());

            if (supporting_file_names != null)
                request.supportingFiles = supporting_file_names;

            JobMilestone milestone=jobMilestoneRepository.findById(id).orElse(null);
            if(milestone!=null){
                milestone.setStatus(JobMilestoneStatus.PA);
                jobMilestoneRepository.save(milestone);

                //fire notification
                Job currentJob=jobRepository.findById(request.getJobId()).orElse(null);
                if(currentJob!=null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody(currentUser.getUserFullName()+ " submitted milestone for your review and approval");
                    body.setSubject("Milestone Review");
                    body.setActionType("REDIRECT");
                    body.setAction("/my-job/contract/milestones/"+request.getJobId()+"/"+request.getProposalId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setRecipient(request.getEmployerId());
                    notificationSender.pushNotification(body);
                }
                //end
                return jobProjectSubmissionRepository.save(request);
            }
            else {
                logger.info("JOBSERVICE: Milestone not found");
                return  null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public JobContract endContract(OAuth2Authentication authentication,Long jobId, Long proposalId, Long userId,  int state) {
        try {
            Job job = jobRepository.findById(jobId).orElse(null);
            JobProposal proposal= jobProposalRepository.findProposalByUserId(jobId,userId);
            JobContract contract = jobContractRepository.findContractByProposalAndJobId(proposalId,jobId);
            JobProjectSubmission project = jobProjectSubmissionRepository.findSubmittedProjectByProposalAndUserId(proposalId,jobId);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            ResponseEntity<String> response;

            if(contract!=null) {
                if (state == 1) {
                    contract.setEndDate(new Date());
                    if (job != null)
                        job.setStatus(JobStatus.CO);
                    if (project != null)
                        project.setStatus(JobSubmissionStatus.CO);
                    if (proposal != null) {
                        proposal.setStatus(JobProposalStatus.CO);
                        proposal.setEndDate(new Date());
                    }
                    //start to release escrow amount to freelancer
                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    response = rest.exchange(baseURL + "/Transaction/release?appid=" + appId
                            + "&referenceid=" + contract.getReferenceId()
                            + "&user_email=" + contract.getUserEmail()
                            + "&reasons=" + project.getDescription(), HttpMethod.POST, entity, String.class);
                    //done

                    if(response.getStatusCode().is2xxSuccessful()){
                        //fire notification
                        Job currentJob=jobRepository.findById(project.getJobId()).orElse(null);
                        if(currentJob!=null) {
                            NotificationBody body = new NotificationBody();
                            body.setBody(currentUser.getUserFullName() + " ended your contract and release the sum of "+proposal.getBidAmount()+" to your bank account");
                            body.setSubject("Contract Ended");
                            body.setActionType("REDIRECT");
                            body.setAction("/job/ongoing/details/"+project.getJobId());
                            body.setTopic("'Job'");
                            body.setChannel("S");
                            body.setRecipient(project.getUserId());
                            notificationSender.pushNotification(body);
                        }
                        //end
                    }
                } else {
                    if (job != null)
                        job.setStatus(JobStatus.AC);
                    if (project != null)
                        project.setStatus(JobSubmissionStatus.RE);
                    if (proposal != null) {
                        proposal.setStatus(JobProposalStatus.IA);
                        proposal.setEndDate(new Date());
                    }

                    //start to reqRefund escrow amount to the employer
                    HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                    response = rest.exchange(baseURL + "/Transaction/reqRefund?appid=" + appId
                            + "&referenceid=" + contract.getReferenceId()
                            + "&user_email=" + contract.getUserEmail()
                            + "&reasons=" + project.getDescription(), HttpMethod.POST, entity, String.class);
                    //done
                }

                if (response != null) {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        jobRepository.save(job);
                        jobProposalRepository.save(proposal);
                        jobProjectSubmissionRepository.save(project);
                        jobContractRepository.save(contract);
                        return contract;
                    } else {
                        logger.info("JOBSERVICE: Escrow transaction failed");
                        return null;
                    }
                } else {
                    logger.info("JOBSERVICE: Escrow transaction failed");
                    return null;
                }

            }else{
                logger.info("JOBSERVICE: Contract not found");
                return  null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public JobMilestone modifyMilestoneState(OAuth2Authentication authentication,Long id, String newStatus) {
        try {
            JobMilestone milestone = jobMilestoneRepository.findById(id).orElse(null);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            int status=0;

            if (milestone != null) {
                milestone.setStatus(JobMilestoneStatus.valueOf(newStatus));
                if (JobMilestoneStatus.valueOf(newStatus) == JobMilestoneStatus.AC) {
                    milestone.setStartDate(new Date());
                    JobProposal proposal =jobProposalRepository.findById(milestone.getProposalId()).orElse(null);
                    Job job =jobRepository.findById(proposal.getJobId()).orElse(null);
                    if(proposal!=null){
                        proposal.setStatus(JobProposalStatus.WP);
                        //send milestone amount to escrow
                        JobContract contract=jobContractRepository.findById(proposal.getContractId()).orElse(null);
                        if(contract!=null) {

                            UUID referenceId = UUID.randomUUID();
                            String transferReferenceId=referenceId.toString().replaceAll("-","");
                            int peppFess = (int) (2.5 / 100) * contract.getAmount() + 200;
                            contract.setPeppfees(peppFess);
                            contract.setReferenceId(transferReferenceId);
                            contract.setTransferReferenceId(transferReferenceId);
                            contract.setAppId(appId);
                            contract.setPeppfees(peppFess);

                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(Calendar.DATE, proposal.duration.intValue());
                            milestone.setEndDate(c.getTime());
                            milestone.setStartDate(new Date());

                            //transfer the amount to Escrow
                            JobTransfer transfer=new JobTransfer();
                            transfer.setUserId(proposal.getUserId());
                            transfer.setJobId(proposal.getJobId());
                            transfer.setProposalId(proposal.getId());
                            transfer.setEmployerId(proposal.getEmployerId());
                            transfer.setCreatedAt(new Date());
                            //transfer
                            transfer.setAmount(Integer.parseInt(milestone.getAmount().toString()));
                            transfer.setCurrency("NGN");
                            transfer.setPaymentReference(contract.getTransferReferenceId());
                            transfer.setInitBranchCode("682");
                            //credit
                            transfer.setCreditAccountName(sidekiqAccountName);
                            transfer.setCreditAccountNumber(sidekiqAccountNumber);
                            transfer.setCreditAccountBankCode("032");
                            transfer.setCreditAccountBranchCode("682");
                            transfer.setCreditAccountType("CASA");
                            transfer.setCreditNarration(job.getTitle());
                            //debit
                            transfer.setDebitAccountName(proposal.getAccountName());
                            transfer.setDebitAccountNumber(proposal.getAccountNumber());
                            transfer.setDebitAccountBranchCode(proposal.getBranchCode());
                            transfer.setDebitAccountBranchCode("682");
                            transfer.setDebitAccountType("CASA");
                            transfer.setDebitNarration(job.getTitle());

                            logger.info(new ObjectMapper().writeValueAsString(transfer));
                            UBNFundsTransferResponse transferResponse= bankTransferService.transferUBNtoUBN(transfer);

                            if(transferResponse.getCode().compareTo("00")==0) {
                                //set milestone amount to the escrow
                                HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                                ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/create?appid=" + appId
                                        + "&referenceid=" + contract.getReferenceId()
                                        + "&user_email=" + contract.getUserEmail()
                                        + "&amount=" + contract.getAmount()
                                        + "&country=" + contract.getCountry()
                                        + "&currency=" + contract.getCurrency()
                                        + "&customer_email=" + contract.getUserEmail()
                                        + "&merchant_email=" + contract.getMerchantEmail()
                                        + "&customer_account_number=" + contract.getCustomerAccountNumber()
                                        + "&merchant_account_number=" + contract.getMerchantAccountNumber()
                                        + "&customer_bank_code=" + contract.getCustomerBankCode()
                                        + "&merchant_bank_code=" + contract.getMerchantAccountNumber()
                                        + "&customer_name=" + contract.getCustomerName()
                                        + "&merchant_name=" + contract.getMerchantName()
                                        + "&customer_phone=" + contract.getCustomerPhone()
                                        + "&merchant_phone=" + contract.getMerchantPhone()
                                        + "&peppfees=" + contract.getPeppfees()
                                        + "&startdate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(milestone.getStartDate())
                                        + "&enddate=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(milestone.getEndDate())
                                        + "&transfer_reference_id=" + contract.getTransferReferenceId(), HttpMethod.POST, entity, String.class);
                                if (response.getStatusCode().is2xxSuccessful()) {
                                    status = 1;
                                    logger.info("JOBSERVICE: Escrow transaction succeeded");
                                }
                                else {
                                    status = 0;
                                    logger.info("JOBSERVICE: Escrow transaction failed");
                                }
                                //done
                            }else{
                                status = 0;
                                logger.info("JOBSERVICE: Payment failed");
                                logger.error(transferResponse.getMessage());
                                logger.error(transferResponse.getCode());
                            }
                        }else{
                            status = 0;
                            logger.error("JOBSERVICE: Contract not found");
                        }
                    }else{
                        status=0;
                        logger.error("JOBSERVICE: Proposal not found");
                    }
                }else{
                    status=0;
                    logger.error("JOBSERVICE: Milestone status mismatch");
                }

                if(status==1) {
                    //fire notification 
                    Job currentJob=jobRepository.findById(milestone.getJobId()).orElse(null);
                    if(currentJob!=null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(currentUser.getUserFullName() + " approved the milestone you submitted for "+currentJob.getTitle()+", you can proceed to start working on it");
                        body.setSubject("Milestone Approval");
                        body.setActionType("REDIRECT");
                        body.setAction("/my-job/contract/milestones/"+milestone.getJobId()+"/"+milestone.getProposalId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(milestone.getUserId());
                        notificationSender.pushNotification(body);
                    }
                    //end
                    return   jobMilestoneRepository.save(milestone);
                }
               else{
                    logger.info("JOBSERVICE: Transaction failed");
                    return  null;
                }
            } else {
                logger.info("JOBSERVICE: Milestone request not found");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public JobMilestone approveCompletedMilestone(OAuth2Authentication authentication,Long id, String requestData) {
        try {
            JobProjectSubmission request = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).readValue(requestData, JobProjectSubmission.class);
            JobMilestone milestone= jobMilestoneRepository.findById(id).orElse(null);
            JobContract contract=jobContractRepository.findContractByProposalAndJobId(milestone.getProposalId(), milestone.getJobId());
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            if(milestone!=null){
                milestone.setStatus(JobMilestoneStatus.CO);
                contract.setClearedAmount(milestone.getAmount().intValue());

                jobMilestoneRepository.save(milestone);
                jobContractRepository.save(contract);

                //start to release escrow amount to freelancer
                HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
                ResponseEntity<String> response = rest.exchange(baseURL + "/Transaction/release?appid=" + appId
                        + "&referenceid=" + contract.getReferenceId()
                        + "&user_email=" + contract.getUserEmail()
                        + "&reasons=" + milestone.getDescription(), HttpMethod.POST, entity, String.class);
                //done
                if(response.getStatusCode().is2xxSuccessful()) {

                    //fire notification 
                    Job currentJob=jobRepository.findById(request.getJobId()).orElse(null);
                    if(currentJob!=null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(currentUser.getUserFullName() + " approved the milestone you submitted for "+currentJob.getTitle()+", and the sum of "+milestone.getAmount().toString()+" has been released to your account");
                        body.setSubject("Milestone Completed");
                        body.setActionType("REDIRECT");
                        body.setAction("/my-job/contract/milestones/"+request.getJobId()+"/"+request.getProposalId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(request.getUserId());
                        notificationSender.pushNotification(body);
                    }
                    //end
                    
                    return milestone;
                }else{
                    logger.info("JOBSERVICE: Escrow transaction failed");
                    return  null;
                }
            }else{
                logger.info("JOBSERVICE: Milestone request not found");
                return  null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

