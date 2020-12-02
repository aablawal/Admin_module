package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.JobContract;
import com.unionbankng.future.futurejobservice.entities.JobContractExtension;
import com.unionbankng.future.futurejobservice.entities.JobMilestone;
import com.unionbankng.future.futurejobservice.entities.JobProjectSubmission;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobContractController {

    private  final JobContractService jobContractService;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping("/v1/job/contract/extension/request")
    public ResponseEntity<APIResponse> requestContractExtension(@Valid @RequestBody String extensionRequest) throws JsonProcessingException {
        JobContractExtension response= jobContractService.requestContractExtension(extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/extension/approve")
    public ResponseEntity<APIResponse> approveContractExtension(@Valid @RequestBody String extensionRequest) throws JsonProcessingException {
        JobContractExtension response= jobContractService.approveContractExtension(extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/milestone/add")
    public ResponseEntity<APIResponse> addNewMilestone(@Valid @RequestBody String milestoneRequest) throws JsonProcessingException {
        JobMilestone response= jobContractService.addNewMilestone(milestoneRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/completed/submit/", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitContract(@Valid @RequestParam(value = "data", required=true) String projectData,
                                              @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles) throws IOException {
        JobProjectSubmission response= jobContractService.submitContract(projectData,supportingFiles);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/milestone/completed/submit/{id}", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitCompletedMilestone(@Valid @RequestParam(value = "data", required=true) String projectData, @PathVariable Long id,
                                                      @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles) throws IOException {
        JobProjectSubmission response= jobContractService.submitCompletedMilestone(id,projectData,supportingFiles);
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


    @GetMapping("/v1/job/completed/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> findJobSubmittedByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long userId){
        JobProjectSubmission response= jobContractService.findJobSubmittedByProposalId(proposalId,userId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PutMapping("/v1/job/contract/end/{jobId}/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> endContract(@PathVariable Long jobId,@PathVariable Long proposalId, @PathVariable Long userId, @RequestParam int state){
        JobContract contract= jobContractService.endContract(jobId,proposalId,userId,state);
        if(contract!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, contract));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
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

    @GetMapping("/v1/job/contract/all/milestones/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findAllContractMilestonesByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long jobId){
        List<JobMilestone> response= jobContractService.findAllContractMilestoneByProposalJobId(proposalId,jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PutMapping("/v1/my-job/contract/milestone/state/{id}")
    public ResponseEntity<APIResponse> modifyMilestoneState(@PathVariable Long id, @RequestParam String status){
        JobMilestone milestone= jobContractService.modifyMilestoneState(id,status);
        if(milestone!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, milestone));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/milestone/completed/approval/{id}")
    public ResponseEntity<APIResponse> approveCompletedMilestone(@Valid @RequestBody String request, @PathVariable Long id) throws JsonProcessingException {
        JobMilestone response= jobContractService.approveCompletedMilestone(id,request);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findJobContractByProposalAndJobId(@Valid @PathVariable Long proposalId, @PathVariable Long jobId){
        JobContract response= jobContractService.findJobContractByProposalAndJobId(proposalId,jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }








}
