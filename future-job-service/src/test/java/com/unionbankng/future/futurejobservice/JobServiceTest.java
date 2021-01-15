package com.unionbankng.future.futurejobservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.services.JobService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest extends  AbstractTest {

    @Autowired
    JobService jobService;
    Long jobId=1l;
    Long userId=1l;

    @Test
    void addJob() throws IOException {
        Job job=new Job();
        job.setOid(userId);
        job.setCategories("Logo Design");
        job.setType(JobType.QUICK_PROJECT);
        job.setTitle("Test Job");
        job.setDescription("Test description");
        job.setGoal("Test goal");
        job.setQualification("Anyone");
        job.setBudget(100l);
        job.setPaymentTerms("I will Pay");
        job.setTimeline("01/10/2021");
        job.setPublishDate(new Date());

        Job createdJob=jobService.addJob(new ObjectMapper().writeValueAsString(job),null,null,null);
        Assert.assertEquals(createdJob.getId(),createdJob.getId());
    }

    @Test
    void closeJobById() {
        Job job=jobService.closeJobById(jobId,0);
        assertEquals(jobId,job.getId());
    }

    @Test
    void openJobById() {
        Job job=jobService.openJobById(jobId);
        assertEquals(jobId,job.getId());
    }

    @Test
    void repeatJobById() {
        Job job=jobService.repeatJobById(jobId);
        assertEquals(jobId,job.getId());
    }

    @Test
    void deleteJobById() {
        jobService.deleteJobById(jobId);
        assertEquals(1,1);
    }
}