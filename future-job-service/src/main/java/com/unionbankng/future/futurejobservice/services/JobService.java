package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.JobProposalStatus;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobTeamStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.pojos.NotificationBody;
import com.unionbankng.future.futurejobservice.pojos.TeamMember;
import com.unionbankng.future.futurejobservice.repositories.*;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JobService {

    private final  AppService appService;
    private  final JobRepository jobRepository;
    private  final JobProposalRepository jobProposalRepository;
    private  final FileStoreService fileStoreService;
    private final JobTeamRepository teamRepository;
    private final NotificationSender notificationSender;
    private  final  JobTeamDetailsRepository jobTeamDetailsRepository;
    private Logger logger = LoggerFactory.getLogger(JobService.class);


    public Job addJob( String jobData, String teamData, MultipartFile[] supporting_files, MultipartFile[] nda_files) throws IOException {
        try {
            String supporting_file_names = null;
            String nda_file_names=null;
            Job job = new ObjectMapper().readValue(jobData, Job.class);
            job.setStatus(JobStatus.AC);

            //save files if not null
            if (nda_files!=null)
                nda_file_names = this.fileStoreService.storeFiles(nda_files, job.oid);
            if (supporting_files!=null)
                supporting_file_names = this.fileStoreService.storeFiles(supporting_files, job.oid);

            //cross verify if attached files processed
            if (nda_file_names != null)
                job.ndaFiles = nda_file_names;
            if (supporting_file_names != null)
                job.supportingFiles = supporting_file_names;

            Job savedJob=jobRepository.save(job);
            if(savedJob!=null) {
                if (savedJob.type == JobType.TEAMS_PROJECT) {
                    JobTeam team = new ObjectMapper().readValue(teamData, JobTeam.class);
                    team.setStatus(JobStatus.AC);
                    team.setJobId(savedJob.id);
                    if (team.getSelectedTeam() != null) {
                        for (String teamMemberData : team.getSelectedTeam().split("~")) {
                            logger.info(teamMemberData);
                            if (teamMemberData != null && teamMemberData != "") {
                                TeamMember teamMember = new ObjectMapper().readValue(teamMemberData, TeamMember.class);
                                logger.info(teamMember.toString());

                                if (teamMember != null) {

                                    //get the right user percentage
                                    String percentageValue = teamMember.getPercentage().replaceAll("%", "");
                                    //get end date
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(new Date());
                                    c.add(Calendar.DATE, 7);

                                    //calculate user founds
                                    int percentage = Integer.parseInt(percentageValue);
                                    int money = (int) (savedJob.getBudget() / 100) * percentage;

                                    JobProposal proposal = new JobProposal();
                                    proposal.setUserId(teamMember.getId());
                                    proposal.setJobId(savedJob.id);
                                    proposal.setStatus(JobProposalStatus.PE);
                                    proposal.setEmployerId(savedJob.oid);
                                    proposal.setDurationType("D");
                                    proposal.setDuration(Long.valueOf(7));
                                    proposal.setEndDate(c.getTime());
                                    proposal.setStartDate(new Date());
                                    proposal.setLastModifiedDate(new Date());
                                    proposal.setAbout(teamMember.getFullName());
                                    proposal.setFullName(teamMember.getFullName());
                                    proposal.setEmail(teamMember.getEmail());
                                    proposal.setImg(teamMember.getImg());
                                    proposal.setAccountName(teamMember.getAccountName());
                                    proposal.setAccountNumber(teamMember.getAccountNumber());
                                    proposal.setAccountType(teamMember.getAccountType());
                                    proposal.setBranchCode(teamMember.getBranchCode());
                                    proposal.setWorkMethod("Overall");
                                    proposal.setPreparedCurrency("NGN");
                                    proposal.setBidAmount(Long.valueOf(money));
                                    proposal.setPercentage(Long.valueOf(percentage));
                                    proposal.setIsApplied(false);
                                    proposal.setCreatedAt(new Date());
                                    JobProposal savedProposal = jobProposalRepository.save(proposal);
                                    if (savedProposal != null) {

                                        JobTeamDetails teamMemberDetails = new JobTeamDetails();
                                        teamMemberDetails.setJobId(savedJob.id);
                                        teamMemberDetails.setEmployerId(savedJob.oid);
                                        teamMemberDetails.setUserId(teamMember.getId());
                                        teamMemberDetails.setFullName(teamMember.getFullName());
                                        teamMemberDetails.setEmail(teamMember.getEmail());
                                        teamMemberDetails.setImg(teamMember.getImg());
                                        teamMemberDetails.setStatus(JobTeamStatus.PF);
                                        teamMemberDetails.setProposalId(savedProposal.id);
                                        teamMemberDetails.setAmount(Long.valueOf(money));
                                        teamMemberDetails.setPercentage(Long.valueOf(percentage));
                                        teamRepository.save(team);
                                        jobTeamDetailsRepository.save(teamMemberDetails);

                                        NotificationBody body = new NotificationBody();
                                        body.setBody("Dear " + teamMemberDetails.getFullName() + ", you have been invited to work on " + savedJob.getTitle() + ".");
                                        body.setSubject("Job Invitation");
                                        body.setActionType("REDIRECT");
                                        body.setAction("/job/details/" + savedJob.getId());
                                        body.setTopic("'Job'");
                                        body.setChannel("S");
                                        body.setRecipient(teamMemberDetails.getId());
                                        notificationSender.pushNotification(body);
                                        logger.info("Notification fired");

                                    }
                                }
                            }
//
                        }
                    }
                }
                //fire notification
                Job currentJob = jobRepository.findById(savedJob.getId()).orElse(null);
                if (currentJob != null) {
                    NotificationBody body = new NotificationBody();
                    body.setBody("Your job for " + currentJob.getTitle() + " has been published");
                    body.setSubject("Job Published");
                    body.setActionType("REDIRECT");
                    body.setAction("/job/details/" + savedJob.getId());
                    body.setTopic("'Job'");
                    body.setChannel("S");
                    body.setRecipient(savedJob.getOid());
                    notificationSender.pushNotification(body);
                    logger.info("Notification fired");
                } else {
                    logger.info("Unable to fire notifications");
                }
                //end
                return savedJob;

            }else {
                logger.info("JOBSERVICE: Unable to save Job");
                return null;
            }
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Job closeJobById(Long id, int state){
        Job job =jobRepository.findById(id).orElse(null);
        if(job!=null) {
            if(state==1)
              job.setStatus(JobStatus.CO);
            else
              job.setStatus(JobStatus.IA);

            Page<JobProposal> proposals=jobProposalRepository.findAllByJobId(PageRequest.of(0,Integer.MAX_VALUE),id);
            if(!proposals.isEmpty()){
                proposals.forEach(jobProposal ->{
                    if(state==1)
                        jobProposal.setStatus(JobProposalStatus.CO);
                    else
                        jobProposal.setStatus(JobProposalStatus.IA);
                    jobProposalRepository.save(jobProposal);
                });
            }
            //fire notification
            Job currentJob=jobRepository.findById(job.getId()).orElse(null);
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody("Your job for "+currentJob.getTitle()+" has been closed");
                body.setSubject("Job Closed");
                body.setActionType("REDIRECT");
                body.setAction("/job/details/"+job.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setPriority("NORMAL");
                body.setRecipient(job.getOid());
                notificationSender.pushNotification(body);
                logger.info("Notification fired");
            }else{
                logger.info("Unable to fire notifications");
            }
            return  jobRepository.save(job);
        }else{
            logger.info("JOBSERVICE: Job not found");
            return  null;
        }
    }

    public Job openJobById(Long id){
        Job job =jobRepository.findById(id).orElse(null);
        if(job!=null) {
            job.setStatus(JobStatus.AC);
            return  jobRepository.save(job);
        }else{
            logger.info("JOBSERVICE: Job not found");
            return  null;
        }
    }


    public Job repeatJobById(Long id){
        Job job =jobRepository.findById(id).orElse(null);
        if(job!=null) {
            job.setStatus(JobStatus.AC);
            Page<JobProposal> proposals=jobProposalRepository.findAllByJobId(PageRequest.of(0,Integer.MAX_VALUE),id);
            if(!proposals.isEmpty()){
                proposals.forEach(jobProposal ->{
                    jobProposalRepository.deleteById(jobProposal.id);
                });
            }
            //fire notification
            Job currentJob=jobRepository.findById(job.getId()).orElse(null);
            if(currentJob!=null) {
                NotificationBody body = new NotificationBody();
                body.setBody("Your job for "+currentJob.getTitle()+" has been Re-published");
                body.setSubject("Job Published");
                body.setActionType("REDIRECT");
                body.setAction("/job/details/"+job.getId());
                body.setTopic("'Job'");
                body.setChannel("S");
                body.setPriority("NORMAL");
                body.setRecipient(job.getOid());
                notificationSender.pushNotification(body);
            }
            //end
            return  jobRepository.save(job);
        }else{
            logger.info("JOBSERVICE: Job not found");
            return  null;
        }
    }
    public void  deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }
    public Model findJobById(Long id, Model model) {
        Job job = jobRepository.findById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return appService.getJob(job, model);
    }
    public Model getJobByInvitationId(String  invitationId, Model model) {
        Job job = jobRepository.findJobByInvitationId(invitationId).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return appService.getJob(job, model);
    }

    public Model getJobs(Pageable pageable, Model model){
        Page<Job> paginatedData=jobRepository.findAll(pageable);
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }
    public Model findJobsByOwnerId(Long id,Pageable pageable, Model model) {
        Page<Job> paginatedData= jobRepository.findByOid(pageable, id);
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }
    public Model findJobsByUserIdAndStatus(Long id,String status, Pageable pageable, Model model) {
        Page<Job> paginatedData= jobRepository.findJobsByUserIdAndStatus(pageable, id, status);
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }
    public Model findJobsByOwnerIdAndStatus(Long id,String status, Pageable pageable, Model model) {
        Page<Job> paginatedData= jobRepository.findJobsByOwnerIdAndStatus(pageable, id, status);
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }

    public Model findJobsByType(JobType type, Pageable pageable, Model model) {
        Page<Job> paginatedData= jobRepository.findByType(pageable, type.name());
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }

    public Long getTotalJobPostedByUserId(Long userId){
        return  jobRepository.getTotalJobPostedByUserId(userId).orElse(0l);
    }
}
