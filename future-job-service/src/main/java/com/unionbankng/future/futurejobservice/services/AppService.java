package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.entities.JobTeamDetails;
import com.unionbankng.future.futurejobservice.enums.JobType;
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
    private final JobTeamDetailsRepository jobTeamDetailsRepository;


    public Model getPaginated(Page page, List data, Model model){
        model.addAttribute("data",data);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        return  model;
    }

    public Model getJob(Job job, Model model){
        model.addAttribute("job",job);
        if(job.getType()== JobType.TEAMS_PROJECT)
            model.addAttribute("teams", jobTeamDetailsRepository.findByJobId(job.id));
        else
            model.addAttribute("teams",null);
        return  model;
    }

    public  Model getJobCollection(Page<Job> page,Model model){
        ArrayList<Map<String,Object>> baseList=new ArrayList<>();
        for (Job job: page.getContent()) {
            Map<String,Object> data=new HashMap<>();
            data.put("job",job);
            data.put("proposals", jobProposalRepository.getCountByJobId(job.id));
            if(job.getType()== JobType.TEAMS_PROJECT)
                data.put("teams", jobTeamDetailsRepository.findByJobId(job.id));
            else
                data.put("teams",null);
            baseList.add(data);
        }
        return  this.getPaginated(page,baseList,model);
    }

    public Model getProposal(JobProposal proposal, Model model){
        model.addAttribute("teams", jobTeamDetailsRepository.findByJobId(proposal.jobId));
        model.addAttribute("job",jobRepository.findById(proposal.jobId));
        model.addAttribute("proposal",proposal);
        return  model;
    }
    public  Model getProposalCollection(Page<JobProposal> page, Model model){
        ArrayList<Map<String,Object>> baseList=new ArrayList<>();
        for (JobProposal proposal: page.getContent()) {
            Map<String,Object> data=new HashMap<>();
            data.put("proposal", proposal);
            data.put("teams",  jobTeamDetailsRepository.findByJobId(proposal.jobId));
            data.put("job",jobRepository.findById(proposal.jobId));
            baseList.add(data);
        }
        return  this.getPaginated(page,baseList,model);
    }
}
