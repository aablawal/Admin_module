package com.unionbankng.future.futureloanservice;

import com.unionbankng.future.futureloanservice.entities.JobProposal;
import com.unionbankng.future.futureloanservice.enums.JobProposalStatus;
import com.unionbankng.future.futureloanservice.services.JobProposalService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JobProposalServiceTest {

    JobProposalService jobProposalService;
    Long jobId=1l;
    Long userId=1l;
    Long proposalId=1l;

    @Test
    void applyJob() {

        JobProposal application= new JobProposal();

        application.setJobId(jobId);
        application.setEmployerId(userId);
        application.setFullName("Test User");
        application.setStatus(JobProposalStatus.PE);
        application.setBidAmount(1l);
        application.setComment("Requiesred");
        application.setPaymentTerms("I will Pay");
        application.setIsApplied(true);
        application.setEmail("test@email.com");
        application.setDuration(2l);
        application.setDurationType("D");
        application.setAbout("About test");
        application.setWorkMethod("Overall");
        application.setPreparedCurrency("NG");
        application.setAccountType("CASA");
        application.setAccountName("OLANLOKUN LANRE");
        application.setAccountNumber("0040553624");
        application.setBranchCode("032");

        JobProposal updatedProposal= jobProposalService.applyJob(application,null);
        Assert.assertEquals(jobId,updatedProposal.getId());
    }

    @Test
    void updateJobProposalStatus() {
        JobProposal updatedProposal=jobProposalService.updateJobProposalStatus(proposalId, JobProposalStatus.CO);
        Assert.assertEquals(Integer.valueOf(1),updatedProposal.getId());
    }

    @Test
    void cancelJobProposal() {
        JobProposal canceledProposal=jobProposalService.cancelJobProposal(jobId,userId);
        Assert.assertEquals(Integer.valueOf(1),canceledProposal.getId());
    }

    @Test
    void declineJobProposal() {
        JobProposal declinedProposal=jobProposalService.declineJobProposal(proposalId);
        Assert.assertEquals(Integer.valueOf(1),declinedProposal.getId());
    }

    @Test
    void changeProposalPercentage() {
        JobProposal updatedProposal=jobProposalService.changeProposalPercentage(proposalId,10);
        Assert.assertEquals(Integer.valueOf(1),updatedProposal.getId());
    }
}