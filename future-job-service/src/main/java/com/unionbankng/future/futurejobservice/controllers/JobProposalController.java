package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.entities.JobContract;
import com.unionbankng.future.futurejobservice.entities.JobTeamDetails;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import com.unionbankng.future.futurejobservice.services.JobProposalService;
import com.unionbankng.future.futurejobservice.services.JobTeamDetailsService;
import com.unionbankng.future.futurejobservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobProposalController {

    private final JobTeamDetailsService jobTeamDetailsService;
    private final JobContractService approveJobProposal;
    private final JobProposalService service;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(JobProposalController.class);


    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping(value = "/v1/job/apply", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse> applyJob(@Valid @RequestParam(value = "data", required = true) String proposalData,
                                                             @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles,  Model model,OAuth2Authentication authentication) throws JsonProcessingException {
          JobProposal appliedJob = service.applyJob(authentication, proposalData, supportingFiles,  model);
        if (appliedJob != null) {
            logger.info("Success");
            return ResponseEntity.ok().body(new APIResponse("success", true, appliedJob));
        }
        else {
            logger.info("Failed");
            return ResponseEntity.ok().body(new APIResponse("failed", false, null));
        }
    }

    @GetMapping("/v1/job/proposal/{id}")
    public ResponseEntity<APIResponse> getProposalById(@PathVariable Long id, Model model) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.findProposalById(id, model)));
    }


    @GetMapping("/v1/user/job/proposal/{jobId}/{userId}")
    public ResponseEntity<APIResponse> getProposalByUserId(@PathVariable Long jobId, @PathVariable Long userId, Model model) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.findProposalByUserId(jobId, userId, model)));
    }

    @GetMapping("/v1/job/proposals/{jid}")
    public ResponseEntity<APIResponse<Optional<List<JobProposal>>>> getJobsByJobId(@PathVariable Long jid, @RequestParam int page, @RequestParam int size, Model model) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.findProposalsByJobId(jid, PageRequest.of(page, size), model)));
    }

    @GetMapping("/v1/jobs/applied/{id}")
    public ResponseEntity<APIResponse<List<Job>>> getAppliedJobByUserId(@PathVariable Long id, @RequestParam int page, @RequestParam int size, Model model) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.findAppliedJobsByUserId(id, PageRequest.of(page, size), model)));
    }

    @GetMapping("/v1/job/proposals/count/{jid}")
    public ResponseEntity<APIResponse<Long>> getProposalsCountByJobId(@PathVariable Long jid) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.getProposalsCount(jid)));
    }

    @PutMapping("/v1/job/proposal/status")
    public ResponseEntity<APIResponse> updateProposalStatusById(@RequestParam Long id, @RequestParam String status, Model model, @ApiIgnore OAuth2Authentication authentication) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.updateJobProposalStatus(authentication, id, status, model)));
    }

    @PostMapping("/v1/job/proposal/approve")
    public ResponseEntity<APIResponse> approveJobProposal(@Valid @RequestBody String approvalRequest, Model model, @ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        JobContract approval = approveJobProposal.approveJobProposal(authentication, approvalRequest, model);
        if (approval != null)
            return ResponseEntity.ok().body(
                    new APIResponse("success", true, approval));
        else
            return ResponseEntity.ok().body(
                    new APIResponse("failed", false, null));
    }

    @PutMapping("/v1/job/proposal/cancel")
    public ResponseEntity<APIResponse> cancelJobProposal(@RequestParam Long jid, @RequestParam Long uid,@ApiIgnore OAuth2Authentication authentication) {
        JobProposal canceledProposal = service.cancelJobProposal(authentication,jid, uid);
        if (canceledProposal != null)
            return ResponseEntity.ok().body(
                    new APIResponse("success", true, canceledProposal));
        else
            return ResponseEntity.ok().body(
                    new APIResponse("failed", false, null));
    }

    @PutMapping("/v1/job/proposal/change/percentage")
    public ResponseEntity<APIResponse> changePercentage(@RequestParam Long pid, @RequestParam int percentage,@ApiIgnore OAuth2Authentication authentication) {
        JobProposal updateProposal = service.changeProposalPercentage(authentication,pid, percentage);
        if (updateProposal != null)
            return ResponseEntity.ok().body(
                    new APIResponse("success", true, updateProposal));
        else
            return ResponseEntity.ok().body(
                    new APIResponse("failed", false, null));
    }


    @GetMapping("/v1/job/teams/{jobId}")
    public ResponseEntity<APIResponse> findTeamsByJobId(@PathVariable Long jobId){
        List<JobTeamDetails> response= jobTeamDetailsService.findTeamsByJobId(jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

}

