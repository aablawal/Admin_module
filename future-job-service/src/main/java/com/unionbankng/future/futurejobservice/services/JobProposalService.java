package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.enums.JobProposalStatus;
import com.unionbankng.future.futurejobservice.repositories.JobProposalRepository;
import lombok.RequiredArgsConstructor;
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

    public JobProposal applyJob(String applicationData, MultipartFile[] supporting_files){
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

            return repository.save(application);

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
        if(proposal!=null)
            this.updateJobProposalStatus(proposal.id,"CA",model);
        return proposal;
    }

    public Model findProposalById(Long id,Model model) {
        JobProposal proposal = repository.findById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Proposal Available"));
        return appService.getProposal(proposal, model);
    }

    public Model findProposalByUserId(Long jobId, Long userId,Model model) {
        JobProposal proposal = repository.findProposalByUserId(jobId, userId);
        return appService.getProposal(proposal, model);
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
