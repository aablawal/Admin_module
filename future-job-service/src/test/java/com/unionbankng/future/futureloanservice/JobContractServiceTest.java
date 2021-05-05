package com.unionbankng.future.futureloanservice;

import com.unionbankng.future.futureloanservice.entities.*;
import com.unionbankng.future.futureloanservice.enums.JobExtensionStatus;
import com.unionbankng.future.futureloanservice.enums.JobMilestoneStatus;
import com.unionbankng.future.futureloanservice.enums.JobSubmissionStatus;
import com.unionbankng.future.futureloanservice.pojos.APIResponse;
import com.unionbankng.future.futureloanservice.services.JobContractService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void approveJobProposal() {
        JobContract contract= new JobContract();
        //set employer account details
        contract.setMerchantName("OLANLOKUN LANRE");
        contract.setMerchantAccountNumber("0040553624");
        contract.setMerchantEmail("test@email.com");
        contract.setMerchantPhone("080800000000'");
        contract.setMerchantBankCode("766");
        //set freelancer account details
        contract.setCustomerName("Test User");
        contract.setCustomerAccountNumber("0040553621");
        contract.setCustomerEmail("test@email.com");
        contract.setCustomerPhone("0808111111111");
        contract.setCustomerBankCode("766");
       //set contract details
        contract.setAmount(50);
        contract.setCountry("NG");
        contract.setCurrency("NGN");
        contract.setJobId(jobId);
        contract.setProposalId(proposalId);
        contract.setUserEmail("test@email.com");
        contract.setWorkMethod("Overall");
        contract.setEndDate(new Date());
        contract.setStartDate(new Date());

        APIResponse response= jobContractService.approveJobProposal("Test User",contract);
        Assert.assertEquals(true,response.isSuccess());

    }

    @Test
    void addNewMilestone() {
        JobMilestone milestone =new JobMilestone();
        milestone.setUserId(userId);
        milestone.setJobId(jobId);
        milestone.setProposalId(proposalId);
        milestone.setTitle("New Milestone");
        milestone.setDescription("I will design the first Logo");
        milestone.setStatus(JobMilestoneStatus.PA);
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
        extensionRequest.setStatus(JobExtensionStatus.AC);
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
        extensionRequest.setStatus(JobExtensionStatus.AC);
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
        request.setStatus(JobSubmissionStatus.AC);
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
        request.setStatus(JobSubmissionStatus.PE);
        request.setLink("www.projectlink.com");
        request.setCreatedAt(new Date());
        JobProjectSubmission savedMilestone= jobContractService.submitCompletedMilestone("Test User", milestoneId,request,null);
        Assert.assertEquals("www.projectlink.com",savedMilestone.getLink());
    }

    @Test
    void endContract() {
        Rate rate=new Rate();
        rate.setRate(5l);
        rate.setDescription("Very Good");
        rate.setFeedback("You have done a good job");
        rate.setUserId(1l);
        JobContract contract=   jobContractService.endContract("Test User",rate,jobId,proposalId,userId,1);
        Assert.assertEquals("NGN",contract.getCountry());
    }

    @Test
    void modifyMilestoneState() {
        APIResponse response= jobContractService.modifyMilestoneState("Test User",milestoneId, JobMilestoneStatus.AC);
        Assert.assertEquals(true,response.isSuccess());
    }
    @Test
    void approveCompletedMilestone() {
        JobProjectSubmission request = new JobProjectSubmission();
        request.setJobId(jobId);
        request.setProposalId(proposalId);
        request.setUserId(1l);
        request.setEmployerId(1l);
        request.setDescription("Test description");
        request.setStatus(JobSubmissionStatus.AC);
        request.setLink("www.projectlink.com");
        request.setCreatedAt(new Date());
        APIResponse response= jobContractService.approveCompletedMilestone("Test User",milestoneId, request);
        Assert.assertEquals(true,response.isSuccess());
    }
}