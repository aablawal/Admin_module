package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.entities.JobContract;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import com.unionbankng.future.futurejobservice.services.JobProposalService;
import com.unionbankng.future.futurejobservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobProposalController {

    private final JobContractService approveJobProposal;
    private final JobProposalService service;
    private final UserService userService;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping(value = "/v1/job/apply", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse<JobProposal>> applyJob(@Valid @RequestParam(value = "data", required = true) String ProposalData,
                                                             @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles) throws IOException {
        JobProposal appliedJob = service.applyJob(ProposalData, supportingFiles);
        if (appliedJob != null)
            return ResponseEntity.ok().body(new APIResponse("success", true, appliedJob));
        else
            return ResponseEntity.ok().body(new APIResponse("failed", false, null));
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
    public ResponseEntity<APIResponse> updateProposalStatusById(@RequestParam Long id, @RequestParam String status, Model model) {
        return ResponseEntity.ok().body(
                new APIResponse("success", true, service.updateJobProposalStatus(id, status, model)));
    }

    @PostMapping("/v1/job/proposal/approve")
    public ResponseEntity<APIResponse> approveJobProposal(@Valid @RequestBody String approvalRequest, Model model) throws JsonProcessingException {
        JobContract approval = approveJobProposal.approveJobProposal(approvalRequest, model);
        if (approval != null)
            return ResponseEntity.ok().body(
                    new APIResponse("success", true, approval));
        else
            return ResponseEntity.ok().body(
                    new APIResponse("failed", false, null));
    }

    @PutMapping("/v1/job/proposal/cancel")
    public ResponseEntity<APIResponse> cancelJobProposal(@RequestParam Long jid, @RequestParam Long uid, Model model) {
        JobProposal canceledProposal = service.cancelJobProposal(jid, uid, model);
        if (canceledProposal != null)
            return ResponseEntity.ok().body(
                    new APIResponse("success", true, canceledProposal));
        else
            return ResponseEntity.ok().body(
                    new APIResponse("failed", false, null));
    }
}

