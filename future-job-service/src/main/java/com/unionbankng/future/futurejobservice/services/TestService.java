package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.Test;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.repositories.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private  final TestRepository repository;
    private final JobRepository jobRepository;

    public Test addTest(Test test) {
        return repository.save(test);
    }
    public Job addDataJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Test> getTests(){
        return repository.findAll();
    }

}
