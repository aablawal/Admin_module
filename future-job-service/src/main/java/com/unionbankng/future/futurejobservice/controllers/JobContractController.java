package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.ContractRequest;
import com.unionbankng.future.futurejobservice.services.UBNBankTransferService;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobContractController {

    private  final JobContractService jobContractService;
    private final UBNBankTransferService bankTransferService;
    Logger logger = LoggerFactory.getLogger(JobContractController.class);


    @PostMapping("/v1/job/contract/extension/request")
    public ResponseEntity<APIResponse> requestContractExtension(@Valid @RequestBody String extensionRequest,@ApiIgnore Principal principal) throws JsonProcessingException {
        JobContractExtension response= jobContractService.requestContractExtension(principal, extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/extension/approve")
    public ResponseEntity<APIResponse> approveContractExtension(@Valid @RequestBody String extensionRequest, @ApiIgnore Principal principal) throws JsonProcessingException {
        JobContractExtension response= jobContractService.approveContractExtension(principal, extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/milestone/add")
    public ResponseEntity<APIResponse> addNewMilestone(@Valid @RequestBody String milestoneRequest, @ApiIgnore Principal principal) throws JsonProcessingException {
        JobMilestone response= jobContractService.addNewMilestone(principal, milestoneRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/completed/submission/", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitContract(@Valid @RequestParam(value = "data", required=true) String projectData,
                                              @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles, @ApiIgnore Principal principal) throws IOException {
        JobProjectSubmission response= jobContractService.submitJob(principal, projectData,supportingFiles);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/contract/raise/dispute", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> raiseDispute(@Valid @RequestParam(value = "data") String projectData,
                                                      @RequestParam(value = "attachment", required = false) MultipartFile[] supportingFiles, @ApiIgnore Principal principal) throws IOException {
        return ResponseEntity.ok().body(jobContractService.raiseDispute(principal, projectData, supportingFiles));
    }


    @PutMapping("/v1/job/completed/rejection/{jobId}/{requestId}")
    public ResponseEntity<APIResponse> rejectJobDone(@PathVariable Long jobId, @PathVariable Long requestId, @ApiIgnore Principal principal){
        JobProjectSubmission request= jobContractService.rejectJob(principal, jobId,requestId);

        if(request!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, request));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/completed/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> findJobSubmittedByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long userId){
        JobProjectSubmission response= jobContractService.findJobSubmittedByProposalId(proposalId,userId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }
    @GetMapping("/v1/job/completed/{contractReference}")
    public ResponseEntity<APIResponse> findJobSubmittedByContractReference(@Valid @PathVariable String contractReference){
        JobProjectSubmission response= jobContractService.findJobSubmittedByContractReference(contractReference);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/milestone/completed/submit/{id}", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitCompletedMilestone(@Valid @RequestParam(value = "data", required=true) String projectData, @PathVariable Long id,
                                                      @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles, @ApiIgnore Principal principal) throws IOException {
        JobProjectSubmission response= jobContractService.submitCompletedMilestone(principal, id,projectData,supportingFiles);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/extension/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> findContractExtensionByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long userId){
        JobContractExtension response= jobContractService.findContractExtensionByProposalId(proposalId,userId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/extension/{contractReference}")
    public ResponseEntity<APIResponse> findContractExtensionByContractReference(@Valid @PathVariable String contractReference){
        JobContractExtension response= jobContractService.findContractExtensionByContractReference(contractReference);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PutMapping("/v1/job/contract/end/{jobId}/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> endContract(@RequestBody Rate rating, @PathVariable Long jobId, @PathVariable Long proposalId, @PathVariable Long userId, @RequestParam int state, @ApiIgnore Principal principal) {
        APIResponse response = jobContractService.endContract(principal, rating, jobId, proposalId, userId, state);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/v1/job/contract/milestone/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> findContractMilestoneByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long userId){
        JobMilestone response= jobContractService.findContractMilestoneByProposalId(proposalId,userId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/milestones/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findAllContractMilestonesByProposalIdAndStatus(@Valid @PathVariable Long proposalId, @PathVariable Long jobId, @RequestParam  String status){
        List<JobMilestone> response= jobContractService.findAllContractMilestoneByProposalJobIdAndStatus(proposalId,jobId,status);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/milestones/spent/amount/{contractReference}")
    public ResponseEntity<APIResponse<Long>> findTotalMilestonesSpentAmountByContractReference(@Valid @PathVariable String contractReference){
        Long amount=jobContractService.findTotalSpentAmountByContractReference(contractReference);
        if(amount!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true,amount));
        else
            return ResponseEntity.ok().body(new APIResponse("success",false,null));
    }

    @GetMapping("/v1/job/contract/all/milestones/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findAllContractMilestonesByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long jobId){
        List<JobMilestone> response= jobContractService.findAllContractMilestoneByProposalJobId(proposalId,jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }


    @PutMapping("/v1/my-job/contract/milestone/state/{id}")
    public ResponseEntity<APIResponse> modifyMilestoneState(@PathVariable Long id, @RequestParam String status, @ApiIgnore Principal principal){
        APIResponse response= jobContractService.modifyMilestoneState(principal,id,status);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/v1/job/milestone/completed/approval/{milestoneReference}")
    public ResponseEntity<APIResponse> approveCompletedMilestone(@PathVariable String milestoneReference,@ApiIgnore Principal principal) {
        logger.info(milestoneReference);
        APIResponse response= jobContractService.approveCompletedMilestone(principal, milestoneReference);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/job/contract/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findJobContractByProposalAndJobId(@PathVariable Long proposalId, @PathVariable Long jobId){
        JobContract response= jobContractService.findJobContractByProposalAndJobId(proposalId,jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/milestone/{milestoneId}")
    public ResponseEntity<APIResponse> findMilestoneById(@PathVariable Long milestoneId){
        JobMilestone milestone= jobContractService.findMilestoneById(milestoneId);
        if(milestone!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, milestone));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/total/job/earning/by/{id}")
    public ResponseEntity<APIResponse> getTotalJobEarningByUser(@PathVariable Long id){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,jobContractService.getTotalJobEarningByUserId(id)));
    }

    @GetMapping("/v1/total/job/expenditure/by/{id}")
    public ResponseEntity<APIResponse> getTotalJobExpenditureByUser(@PathVariable Long id){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,jobContractService.getTotalJobExpenditureByUser(id)));
    }

    @GetMapping("/v1/total/job/completed/by/{id}")
    public ResponseEntity<APIResponse> getTotalJobCompletedByUser(@PathVariable Long id){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,jobContractService.getTotalJobCompletedByUser(id)));
    }

    @PostMapping("/v1/job/contract/settlement")
    public ResponseEntity<APIResponse> settleContractPaymentById(@Valid @RequestBody ContractRequest contractRequest, Principal principal) throws CloneNotSupportedException {
        return ResponseEntity.ok().body(jobContractService.settleContractById(principal, contractRequest.getContractReference()));
    }


    @PostMapping("/v1/job/contract/reversal")
    public ResponseEntity<APIResponse> reverseContractPaymentById(@Valid @RequestBody ContractRequest contractRequest, Principal principal) throws JsonProcessingException {
        return ResponseEntity.ok().body(jobContractService.reverseContractPaymentById(principal, contractRequest.getContractReference()));
    }

}
