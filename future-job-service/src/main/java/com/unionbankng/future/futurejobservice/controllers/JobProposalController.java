package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.JobProposal;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.services.DatabaseService;
import com.unionbankng.future.futurejobservice.services.EscrowService;
import com.unionbankng.future.futurejobservice.services.JobProposalService;
import com.unionbankng.future.futurejobservice.services.UserService;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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

    private final JobProposalService service;
    private final EscrowService escrowService;
    private final UserService userService;
    private final DatabaseService databaseService;
    private Object data;

    @ModelAttribute
    public void serResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping(value="/v1/job/apply", consumes="multipart/form-data")
    public ResponseEntity<APIResponse<JobProposal>> applyJob(@Valid @RequestParam(value = "data", required=true) String ProposalData,
                                                             @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles) throws IOException {
        JobProposal appliedJob=service.applyJob(ProposalData,supportingFiles);
        if(appliedJob!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true,appliedJob));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false,null));
    }

    @GetMapping("/v1/job/proposal/{id}")
    public ResponseEntity<APIResponse> getProposalById(@PathVariable Long id, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findProposalById(id,model)));
    }

    @GetMapping("/v1/job/proposals/{jid}")
    public ResponseEntity<APIResponse<Optional<List<JobProposal>>>> getJobsByOwnerId(@PathVariable Long jid, @RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findProposalsByJobId(jid, PageRequest.of(page,size),model)));
    }

    @GetMapping("/v1/jobs/applied/{id}")
    public ResponseEntity<APIResponse<List<Job>>> getAppliedJobByUserId(@PathVariable Long id, @RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findAppliedJobsByUserId(id, PageRequest.of(page,size), model)));
    }

    @GetMapping("/v1/job/proposals/count/{jid}")
    public ResponseEntity<APIResponse<Long>> getProposalsCountByJobId(@PathVariable Long jid){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.getProposalsCount(jid)));
    }
    @PutMapping("/v1/job/proposal/status")
    public ResponseEntity<APIResponse> updateProposalStatusById(@RequestParam Long id, @RequestParam String status, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true, service.updateProposalStatus(id,status, model)));
    }

    @GetMapping("/v1/jwt/test")
    public ResponseEntity<APIResponse> test(OAuth2Authentication authentication){
     return ResponseEntity.ok().body(
                new APIResponse("success",true, databaseService.connect()));
    }

}

