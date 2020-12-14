package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.enums.JobProposalStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.repositories.JobProposalRepository;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JobProposalService  implements Serializable {

    private final AppService appService;
    private  final JobProposalRepository repository;
    private  final FileStoreService fileStoreService;
    private final JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(JobProposalService.class);
    private final NotificationSender notificationSender;


    public JobProposal applyJob(OAuth2Authentication authentication,String applicationData, MultipartFile[] supporting_files,  Model model){
        try {
            String supporting_file_names = null;
            JobProposal application = new ObjectMapper().readValue(applicationData, JobProposal.class);
            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

            application.status= JobProposalStatus.PE;
            application.setIsApplied(true);
            application.setCreatedAt(new Date());
            boolean isEdited=false;

            //save files if not null
            if (supporting_files!=null)
                supporting_file_names = this.fileStoreService.storeFiles(supporting_files, application.userId);
            //cross verify if attached files processed
            if (supporting_file_names != null)
                application.supportingFiles = supporting_file_names;
            if(application.id!=null)
                isEdited=true;

            JobProposal proposal= repository.save(application);
            if(proposal!=null) {
                Job job = jobRepository.findById(proposal.jobId).orElse(null);
                if (job != null) {
                    if (job.type == JobType.TEAMS_PROJECT) {
                        //calculate user founds
                        int percentage=10; //default percentage
                        int bidAmount = (int)(job.getBudget() / 100)*percentage;
                        proposal.setBidAmount(Long.valueOf(bidAmount));
                        proposal.setPercentage(Long.valueOf(percentage));
                        proposal=repository.save(proposal);
                    }

                    if(!isEdited) {
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

                    return  proposal;
                }else{
                    logger.info("JOBSERVICE: Unable to save Job");
                    return  null;
                }
            }else{
                logger.info("JOBSERVICE: Unable to save Proposal");
                return null;
            }

        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JobProposal updateJobProposalStatus(OAuth2Authentication authentication,Long id, String newStatus, Model model){
        Model data =this.findProposalById(id,model);
        JobProposal proposal= (JobProposal) data.getAttribute("proposal");
        proposal.setStatus(JobProposalStatus.valueOf(newStatus.toUpperCase()));
        return repository.save(proposal);
    }

    public JobProposal cancelJobProposal(OAuth2Authentication authentication,Long jobId, Long userId, Model model){
        Model data =this.findProposalByUserId(jobId,userId,model);
        JobProposal proposal= (JobProposal) data.getAttribute("proposal");
        if(proposal!=null) {
            this.updateJobProposalStatus(authentication,proposal.id, "CA", model);
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
        Page<JobProposal> paginatedData= repository.findByUserId(pageable, userid);
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
