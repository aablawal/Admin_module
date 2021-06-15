package com.unionbankng.future.futurejobservice;

import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.enums.Status;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
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
    void addNewMilestone() {
        JobMilestone milestone =new JobMilestone();
        milestone.setUserId(userId);
        milestone.setJobId(jobId);
        milestone.setProposalId(proposalId);
        milestone.setTitle("New Milestone");
        milestone.setDescription("I will design the first Logo");
        milestone.setStatus(Status.PA);
        milestone.setAmount(1l);
        milestone.setEndDate(new Date());
        milestone.setStartDate(new Date());
        milestone.setCreatedAt(new Date());
        JobMilestone savedRequest=jobContractService.addNewMilestone("Test User", milestone);
        Assert.assertEquals(jobId,savedRequest.getJobId());
    }

    @Test
    void requestContractExtension(){
        JobContractExtension extensionRequest = new JobContractExtension();
        extensionRequest.setUserId(userId);
        extensionRequest.setProposalId(proposalId);
        extensionRequest.setEmployerId(userId);
        extensionRequest.setJobId(jobId);
        extensionRequest.setStatus(Status.AC);
        extensionRequest.setReason("Unable to make it");
        JobContractExtension savedRequest=jobContractService.requestContractExtension("Test User", extensionRequest);
        Assert.assertEquals(jobId,savedRequest.getJobId());
    }
    @Test
    void approveContractExtension() {
        JobContractExtension extensionRequest = new JobContractExtension();
        extensionRequest.setUserId(userId);
        extensionRequest.setProposalId(proposalId);
        extensionRequest.setEmployerId(userId);
        extensionRequest.setJobId(jobId);
        extensionRequest.setStatus(Status.AC);
        extensionRequest.setReason("Unable to make it");
        JobContractExtension savedRequest=jobContractService.approveContractExtension("Test User", extensionRequest);
        Assert.assertEquals(jobId,savedRequest.getJobId());
    }

    @Test
    void submitJob() {
        JobProjectSubmission request = new JobProjectSubmission();
        request.setJobId(jobId);
        request.setProposalId(proposalId);
        request.setUserId(1l);
        request.setEmployerId(1l);
        request.setDescription("Test description");
        request.setStatus(Status.AC);
        request.setLink("www.projectlink.com");
        request.setCreatedAt(new Date());
        JobProjectSubmission savedRequest= jobContractService.submitJob("Test User",request, null);
        Assert.assertEquals(jobId,savedRequest.getJobId());

    }

    @Test
    void raiseDispute() {
        JobContractDispute dispute=new JobContractDispute();
        dispute.setJobId(jobId);
        dispute.setEmployerId(userId);
        dispute.setUserId(userId);
        dispute.setProposalId(proposalId);
        dispute.setContractId(contractId);
        dispute.setDescription("The work i did is great, i need my money");
        APIResponse response=  jobContractService.raiseDispute("Test User",userId,dispute,null);
        Assert.assertEquals(true,response.isSuccess());
    }

    @Test
    void rejectJob() {
        JobProjectSubmission contract=  jobContractService.rejectJob(null,jobId,requestId);
        Assert.assertEquals(jobId,contract.getJobId());
    }

    @Test
    void submitCompletedMilestone() {
        JobProjectSubmission request = new JobProjectSubmission();
        request.setJobId(jobId);
        request.setProposalId(proposalId);
        request.setUserId(1l);
        request.setEmployerId(1l);
        request.setDescription("Test description");
        request.setStatus(Status.PE);
        request.setLink("www.projectlink.com");
        request.setCreatedAt(new Date());
        JobProjectSubmission savedMilestone= jobContractService.submitCompletedMilestone("Test User", milestoneId,request,null);
        Assert.assertEquals("www.projectlink.com",savedMilestone.getLink());
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