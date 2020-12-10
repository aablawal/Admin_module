package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.entities.JobTeamDetails;
import com.unionbankng.future.futurejobservice.repositories.JobProposalRepository;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.repositories.JobTeamDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppService {

    private final JobProposalRepository jobProposalRepository;
    private final JobRepository jobRepository;
    private final UserService userService;
    private final JobTeamDetailsRepository jobTeamDetailsRepository;


    public Model getPaginated(Page page, List data, Model model){
        model.addAttribute("data",data);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        return  model;
    }

    public  List<Map<String,Object>>  getTeamMembersByJobId(Long jobId){
        List<JobTeamDetails> teamMembers= jobTeamDetailsRepository.findByJobId(jobId);
        List<Map<String,Object>> teams= new ArrayList();
        for (JobTeamDetails teamMember : teamMembers) {
            Map<String,Object> team=new HashMap<>();
            team.put("data",teamMember);
            team.put("user", userService.getUserById(teamMember.getUserId()));
            teams.add(team);
        }
        return  teams;
    }

    public Model getJob(Job job, Model model){
        model.addAttribute("job",job);
        model.addAttribute("teams", this.getTeamMembersByJobId(job.id));
        model.addAttribute("employer",userService.getUserById(job.oid));
        model.addAttribute("proposals", jobProposalRepository.getCountByJobId(job.id));
        return  model;
    }
    public  Model getJobCollection(Page<Job> page,Model model){
        ArrayList<Map<String,Object>> baseList=new ArrayList<>();
        for (Job job: page.getContent()) {
            Map<String,Object> data=new HashMap<>();
            data.put("job",job);
            data.put("teams", this.getTeamMembersByJobId(job.id));
            data.put("employer",userService.getUserById(job.id));
            data.put("proposals", jobProposalRepository.getCountByJobId(job.id));
            baseList.add(data);
        }
        return  this.getPaginated(page,baseList,model);
    }

    public Model getProposal(JobProposal proposal, Model model){

        model.addAttribute("teams", this.getTeamMembersByJobId(proposal.jobId));
        model.addAttribute("job",jobRepository.findById(proposal.jobId));
        model.addAttribute("proposal",proposal);
        model.addAttribute("user", userService.getUserById(proposal.userId));
        model.addAttribute("employer",  userService.getUserById(jobRepository.findById(proposal.jobId).get().oid));

        return  model;
    }
    public  Model getProposalCollection(Page<JobProposal> page, Model model){
        ArrayList<Map<String,Object>> baseList=new ArrayList<>();
        for (JobProposal proposal: page.getContent()) {
            Map<String,Object> data=new HashMap<>();

            data.put("proposal", proposal);
            data.put("teams", this.getTeamMembersByJobId(proposal.jobId));
            data.put("job",jobRepository.findById(proposal.jobId));
            data.put("user",  userService.getUserById(proposal.userId));
            data.put("employer",  userService.getUserById(jobRepository.findById(proposal.jobId).get().oid));
            baseList.add(data);
        }
        return  this.getPaginated(page,baseList,model);
    }

}
