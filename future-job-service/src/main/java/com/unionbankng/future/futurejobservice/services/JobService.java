package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.util.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JobService {

    private final  AppService appService;
    private  final JobRepository jobRepository;
    private  final FileStoreService fileStoreService;


    public Job addJob(String jobData,  MultipartFile[] supporting_files,  MultipartFile[] nda_files) throws IOException {
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
            return savedJob;

        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void  deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }
    public Model findJobById(Long id, Model model) {
        Job job = jobRepository.findById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
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
    public Model findJobsByType(JobType type, Pageable pageable, Model model) {
        Page<Job> paginatedData= jobRepository.findByType(pageable, type);
        Model jobList=appService.getJobCollection(paginatedData,model).addAttribute("currentPage",pageable.getPageNumber());
        return jobList;
    }
}
