package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.enums.Status;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.repositories.JobProposalRepository;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.util.App;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JobProposalService  implements Serializable {

    private final App app;
    private final AppService appService;
    private  final JobProposalRepository repository;
    private  final FileStoreService fileStoreService;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(JobProposalService.class);
    private final NotificationSender notificationSender;


    public JobProposal applyJob(Principal principal, String applicationData, MultipartFile[] supporting_files)throws JsonProcessingException {
        JobProposal application = new ObjectMapper().readValue(applicationData, JobProposal.class);
        return applyJob(application, supporting_files);
    }

    public JobProposal applyJob(JobProposal application, MultipartFile[] supporting_files) {
        try {
            String supporting_file_names = null;
            Job job = jobRepository.findById(application.getJobId()).orElse(null);
            application.setIsApplied(true);
            application.setCreatedAt(new Date());
            application.setLastModifiedDate(new Date());
            boolean isEdited = false;
            if (application.getId()!= null)
                isEdited = true;

            if (isEdited) {
                if (application.getStatus().equals(Status.RE)) {
                    application.setStatus(Status.PE);
                }
            }
            else {
                application.setStatus(Status.PE);
            }



            if (job.getType() == JobType.TEAMS_PROJECT && !isEdited) {
                //calculate user founds
                if (application.getPercentage() == null) {
                    int percentage = 10; //default percentage
                    int bidAmount = Math.round((job.getBudget() / 100) * percentage);
                    application.setBidAmount(Long.valueOf(bidAmount));
                    application.setPercentage(Long.valueOf(percentage));
                }
            }

            //save files if not null
            if (supporting_files != null)
                supporting_file_names = this.fileStoreService.storeFiles(supporting_files, "proposal");
            //cross verify if attached files processed
            if (supporting_file_names != null)
                application.setSupportingFiles(supporting_file_names);


            app.print(application);
            JobProposal proposal = repository.save(application);
            if (proposal != null) {

                if (job != null) {

                    if (!isEdited) {
                        //fire notification
                        Job currentJob = jobRepository.findById(proposal.getJobId()).orElse(null);
                        if (currentJob != null) {
                            NotificationBody body = new NotificationBody();
                            body.setBody("You have new proposal for " + currentJob.getTitle());
                            body.setSubject("New Proposal");
                            body.setActionType("REDIRECT");
                            body.setAction("/my-job/proposals/" + proposal.getJobId());
                            body.setTopic("'Job'");
                            body.setChannel("S");
                            body.setRecipient(proposal.getEmployerId());
                            notificationSender.pushNotification(body);
                        }
                    }
                    //end

                    return proposal;
                } else {
                    logger.info("JOBSERVICE: Unable to save Job");
                    return null;
                }
            } else {
                logger.info("JOBSERVICE: Unable to save Proposal");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JobProposal updateJobStatus(Long proposalId, Status status) {
        JobProposal proposal = repository.findById(proposalId).orElse(null);
        if (proposal != null) {
            proposal.setStatus(status);
            return repository.save(proposal);
        } else {
            return null;
        }
    }

    public JobProposal cancelJobProposal(Long jobId, Long userId){
        JobProposal proposal=repository.findProposalByUserId(jobId,userId);
        if(proposal!=null) {
            repository.delete(proposal);
            //fire notification
            Job currentJob=jobRepository.findById(proposal.getJobId()).orElse(null);
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody("Your proposal for  "+currentJob.getTitle()+" has been canceled");
                body.setSubject("Proposal Canceled");
                body.setActionType("REDIRECT");
                body.setAction("/my-jobs/proposal/preview/"+proposal.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setRecipient(proposal.getUserId());
                notificationSender.pushNotification(body);
            }
            //end
        }
        else {
            logger.info("JOBSERVICE: Proposal not found");
        }
        return proposal;
    }


    public JobProposal declineJobProposal(Long proposalId){
        JobProposal proposal=repository.findById(proposalId).orElse(null);
        if(proposal!=null) {
            proposal.setStatus(Status.RE);
            proposal.setLastModifiedDate(new Date());
            repository.save(proposal);
            //fire notification
            Job currentJob=jobRepository.findById(proposal.getJobId()).orElse(null);
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody("Your proposal for  "+currentJob.getTitle()+" has been rejected");
                body.setSubject("Proposal Rejected");
                body.setActionType("REDIRECT");
                body.setAction("/my-jobs/proposal/preview/"+proposal.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setRecipient(proposal.getUserId());
                notificationSender.pushNotification(body);
            }
            //end
        }
        else {
            logger.info("JOBSERVICE: Proposal not found");
        }
        return proposal;
    }

    public  JobProposal changeProposalPercentage(Long proposalId, int percentage){
        JobProposal proposal=repository.findById(proposalId).orElse(null);
        if(proposal!=null) {
            Job currentJob =jobRepository.findById(proposal.getJobId()).orElse(null);
            int bidAmount = (int) (currentJob.getBudget() / 100) *  percentage;
            proposal.setPercentage(Long.valueOf(percentage));
            proposal.setBidAmount(Long.valueOf(bidAmount));
            repository.save(proposal);
            //fire notification
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody("The responsibility  for  "+currentJob.getTitle()+" has been changed to "+percentage+"% now and amount to NGN "+bidAmount+'.');
                body.setSubject("Responsibility Change");
                body.setActionType("REDIRECT");
                body.setAction("/my-jobs/proposal/preview/"+proposal.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setRecipient(proposal.getUserId());
                notificationSender.pushNotification(body);
            }
            //end
        }
        else {
            logger.info("JOBSERVICE: Proposal not found");
        }
        return proposal;
    }

    public Model findProposalById(Long id,Model model) {
        JobProposal proposal = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Proposal Available"));
        if (proposal != null)
            return appService.getProposal(proposal, model);
        else
            return null;
    }

    public Model findProposalByUserId(Long jobId, Long userId,Model model) {
        JobProposal proposal = repository.findProposalByUserId(jobId, userId);

        if(proposal!=null)
            return appService.getProposal(proposal, model);
        else
            return null;
    }


    public Model findProposalsByJobId(Long jid, Pageable pageable, Model model) {
        Page<JobProposal> paginatedData= repository.findAllByJobId(pageable, jid);
        Model proposalList = appService.getProposalCollection(paginatedData, model).addAttribute("currentPage", pageable.getPageNumber());
        return proposalList;
    }
    public Model findAppliedJobsByUserId(Long userid, Pageable pageable, Model model) {
        Page<JobProposal> paginatedData= repository.findProposalsByUserId(pageable, userid);
        Model proposalList = appService.getProposalCollection(paginatedData, model).addAttribute("currentPage", pageable.getPageNumber());
        return proposalList;
    }

    public Model getProposals(Pageable pageable, Model model){
        Page<JobProposal> paginatedData= repository.findAll(pageable);
        Model proposalList=appService.getProposalCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return proposalList;
    }
    public Long getProposalsCount(Long jid){
        return repository.getCountByJobId(jid);
    }
}
