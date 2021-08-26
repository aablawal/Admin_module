package com.unionbankng.future.futurejobservice;

import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.Status;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.RejectionRequest;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Date;

class JobContractServiceTest {

    @Autowired
    JobContractService jobContractService;
    Long jobId=1l;
    Long proposalId=1l;
    Long milestoneId=1l;
    Long userId=1l;
    Long requestId=1l;
    Long contractId =1l;



    @Test
    void rejectJob() {
        RejectionRequest rejectionRequest = new RejectionRequest();
        rejectionRequest.setReason("Test Reason");
        JobProjectSubmission contract=  jobContractService.rejectJob(null,rejectionRequest,jobId,requestId);
        Assert.assertEquals(jobId,contract.getJobId());
    }

    @Test
    void endContract(Principal principal) {
        Rate rate=new Rate();
        rate.setRate(5l);
        rate.setDescription("Very Good");
        rate.setFeedback("You have done a good job");
        rate.setUserId(1l);
        APIResponse response=   jobContractService.endContract(principal,rate,jobId,proposalId,userId,1);
        Assert.assertEquals(true,response.isSuccess());
    }

    @Test
    void approveCompletedMilestone(Principal principal) {
        APIResponse response= jobContractService.approveCompletedMilestone(principal,"milestonerefrence goes here");
        Assert.assertEquals(true,response.isSuccess());
    }
}