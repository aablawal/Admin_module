package com.unionbankng.future.futurejobservice.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    private  final JobRepository repository;
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
                    job.nda_files = nda_file_names;
                if (supporting_file_names != null)
                    job.supporting_files = supporting_file_names;

            Job savedJob=repository.save(job);
            return savedJob;

        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Job> getJobs(){
        return repository.findAll();
    }
    public Optional<Job> findJobById(Long id) {
        return repository.findById(id);
    }
    public Optional<List<Job>> findJobsByOwnerId(Long id) {
        return repository.findByOid(id,Sort.by("id").descending());
    }
    public  Optional<List<Job>> findJobsByStatus(JobStatus status) {
        return repository.findByStatus(status, Sort.by("id").descending());
    }
    public  Optional<List<Job>> findJobByType(JobType type) {
        return repository.findByType(type, Sort.by("id").descending());
    }
    public void  deleteJobById(Long id) {
        repository.deleteById(id);
    }
}
