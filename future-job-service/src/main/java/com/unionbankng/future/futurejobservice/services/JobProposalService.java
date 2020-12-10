package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.entities.JobTeamDetails;
import com.unionbankng.future.futurejobservice.enums.JobProposalStatus;
import com.unionbankng.future.futurejobservice.enums.JobTeamStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.pojos.User;
import com.unionbankng.future.futurejobservice.repositories.JobProposalRepository;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.repositories.JobTeamDetailsRepository;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JobProposalService  implements Serializable {

    private final AppService appService;
    private  final JobProposalRepository repository;
    private  final FileStoreService fileStoreService;
    private final JobRepository jobRepository;
    private final JobTeamDetailsRepository jobTeamDetailsRepository;
    private Logger logger = LoggerFactory.getLogger(JobProposalService.class);
    private final UserService userService;
    private final NotificationSender notificationSender;


    public JobProposal applyJob(String applicationData, MultipartFile[] supporting_files,  Model model){
        try {
            String supporting_file_names = null;
            JobProposal application = new ObjectMapper().readValue(applicationData, JobProposal.class);
            application.status= JobProposalStatus.PE;

            //save files if not null
            if (supporting_files!=null)
                supporting_file_names = this.fileStoreService.storeFiles(supporting_files, application.userId);
            //cross verify if attached files processed
            if (supporting_file_names != null)
                application.supportingFiles = supporting_file_names;

            JobProposal proposal= repository.save(application);
            if(proposal!=null) {
                Job job = jobRepository.findById(proposal.jobId).orElse(null);
                if (job != null) {
                    if (job.type == JobType.TEAMS_PROJECT) {
                        //calculate user founds
                        int money = (int)(job.getBudget() / 100)*10;

                        JobTeamDetails teamMember = new JobTeamDetails();
                        teamMember.setAmount(proposal.bidAmount);
                        teamMember.setJobId(proposal.jobId);
                        teamMember.setProposalId(proposal.id);
                        teamMember.setEmployerId(proposal.employerId);
                        teamMember.setStatus(JobTeamStatus.PE);
                        teamMember.setDescription(proposal.about);
                        teamMember.setPercentage(Long.valueOf(10));
                        teamMember.setAmount(Long.valueOf(money));
                        jobTeamDetailsRepository.save(teamMember);
                    }

                    //fire notification
                    User currentUser =userService.getUserById(proposal.getUserId());
                    Job currentJob=jobRepository.findById(proposal.getJobId()).orElse(null);
                    if(currentUser!=null && currentJob!=null) {
                        NotificationBody body = new NotificationBody();
                        body.setBody(currentUser.getFullName() + " applied to your  project for "+currentJob.getTitle());
                        body.setSubject("New Proposal");
                        body.setActionType("REDIRECT");
                        body.setAction("/my-job/proposals/"+proposal.getJobId());
                        body.setTopic("'Job'");
                        body.setChannel("S");
                        body.setRecipient(proposal.getEmployerId());
                        notificationSender.sendEmail(body);
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

    public JobProposal updateJobProposalStatus(Long id, String newStatus, Model model){
        Model data =this.findProposalById(id,model);
        JobProposal proposal= (JobProposal) data.getAttribute("proposal");
        proposal.setStatus(JobProposalStatus.valueOf(newStatus.toUpperCase()));
        return repository.save(proposal);
    }

    public JobProposal cancelJobProposal(Long jobId, Long userId, Model model){
        Model data =this.findProposalByUserId(jobId,userId,model);
        JobProposal proposal= (JobProposal) data.getAttribute("proposal");
        if(proposal!=null) {
            this.updateJobProposalStatus(proposal.id, "CA", model);
            //fire notification
            User currentUser =userService.getUserById(proposal.getEmployerId());
            Job currentJob=jobRepository.findById(proposal.getJobId()).orElse(null);
            if(currentUser!=null && currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody(currentUser.getFullName() + " canceled  your proposal for "+currentJob.getTitle());
                body.setSubject("Proposal Canceled");
                body.setActionType("REDIRECT");
                body.setAction("/my-jobs/proposal/preview/"+proposal.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setRecipient(proposal.getUserId());
                notificationSender.sendEmail(body);
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
