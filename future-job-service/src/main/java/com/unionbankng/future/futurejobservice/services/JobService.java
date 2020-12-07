package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.grpc.UserDetailResponse;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.JobProposalStatus;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobTeamStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.User;
import com.unionbankng.future.futurejobservice.repositories.*;
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
    private final JobProjectSubmissionRepository jobProjectSubmissionRepository;
    private final JobNotificationRepository jobNotificationRepository;
    private  final FileStoreService fileStoreService;
    private final JobTeamRepository teamRepository;
    private final UserService userService;
    private  final  JobTeamDetailsRepository jobTeamDetailsRepository;
    private Logger logger = LoggerFactory.getLogger(JobService.class);




    public Job addJob(String jobData, String teamData,  MultipartFile[] supporting_files,  MultipartFile[] nda_files) throws IOException {
        try {
            String supporting_file_names = null;
            String nda_file_names=null;
            Job job = new ObjectMapper().readValue(jobData, Job.class);
            JobTeam team = new ObjectMapper().readValue(teamData,JobTeam.class);
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
                if(savedJob.type==JobType.TEAMS_PROJECT) {
                    team.setStatus(JobStatus.AC);
                    team.setJobId(savedJob.id);
                    if (team.getSelectedTeam() != null)
                        teamRepository.save(team);

                    for (String s : team.getSelectedTeam().split(",")) {
                        if(s!=null && s!=""){
                            String []data =s.split(":");
                            if(data.length>0 ) {
                                Long userId = Long.parseLong(data[0]);
                                int percentage = Integer.parseInt(data[1].replace("%", ""));
                                UserDetailResponse currentUser=userService.getUserById(userId);
                                if(currentUser!=null){

                                    //get end date
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(new Date());
                                    c.add(Calendar.DATE,7);

                                    //calculate user founds
                                    int money = (int)(savedJob.getBudget() / 100)*percentage;

                                    JobProposal proposal =new JobProposal();
                                    proposal.setUserId(currentUser.getId());
                                    proposal.setJobId(savedJob.id);
                                    proposal.setStatus(JobProposalStatus.PE);
                                    proposal.setEmployerId(savedJob.oid);
                                    proposal.setDurationType("D");
                                    proposal.setDuration(Long.valueOf(7));
                                    proposal.setEndDate(c.getTime());
                                    proposal.setStartDate(new Date());
                                    proposal.setLastModifiedDate(new Date());
                                    proposal.setAbout(currentUser.getUsername());
                                    proposal.setAccountName(currentUser.getAccountName());
                                    proposal.setAccountNumber(currentUser.getAccountNumber());
                                    proposal.setWorkMethod("Overall");
                                    proposal.setPreparedCurrency("NGN");
                                    proposal.setBidAmount(Long.valueOf(money));
                                    JobProposal savedProposal=jobProposalRepository.save(proposal);
                                    if(savedProposal!=null) {

                                        JobTeamDetails teamMember = new JobTeamDetails();
                                        teamMember.setJobId(savedJob.id);
                                        teamMember.setEmployerId(savedJob.oid);
                                        teamMember.setUserId(currentUser.getId());
                                        teamMember.setStatus(JobTeamStatus.PF);
                                        teamMember.setProposalId(savedProposal.id);
                                        teamMember.setAmount(Long.valueOf(money));
                                        teamMember.setPercentage(Long.valueOf(percentage));
                                        jobTeamDetailsRepository.save(teamMember);

                                    }
                                }

                            }

                        }
                    }
                    return  savedJob;
                }else{
                    return  savedJob;
                }
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
}
