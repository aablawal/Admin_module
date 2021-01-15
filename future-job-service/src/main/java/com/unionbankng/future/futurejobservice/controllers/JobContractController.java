package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.BankTransferService;
import com.unionbankng.future.futurejobservice.services.JobContractService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobContractController {

    private  final JobContractService jobContractService;
    private final BankTransferService bankTransferService;
    Logger logger = LoggerFactory.getLogger(JobContractController.class);


    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping("/v1/job/contract/extension/request")
    public ResponseEntity<APIResponse> requestContractExtension(@Valid @RequestBody String extensionRequest,@ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        JobContractExtension response= jobContractService.requestContractExtension(authentication, extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/extension/approve")
    public ResponseEntity<APIResponse> approveContractExtension(@Valid @RequestBody String extensionRequest, @ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        JobContractExtension response= jobContractService.approveContractExtension(authentication, extensionRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping("/v1/job/contract/milestone/add")
    public ResponseEntity<APIResponse> addNewMilestone(@Valid @RequestBody String milestoneRequest, @ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        JobMilestone response= jobContractService.addNewMilestone(authentication, milestoneRequest);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/completed/submission/", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitContract(@Valid @RequestParam(value = "data", required=true) String projectData,
                                              @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles, @ApiIgnore OAuth2Authentication authentication) throws IOException {
        JobProjectSubmission response= jobContractService.submitJob(authentication, projectData,supportingFiles);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @PostMapping(value="/v1/job/contract/raise/dispute", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> raiseDispute(@Valid @RequestParam(value = "data") String projectData,
                                                      @RequestParam(value = "attachment", required = false) MultipartFile[] supportingFiles, @ApiIgnore OAuth2Authentication authentication) throws IOException {
        JobContractDispute response= jobContractService.raiseDispute(authentication, projectData,supportingFiles);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }


    @PutMapping("/v1/job/completed/rejection/{jobId}/{requestId}")
    public ResponseEntity<APIResponse> rejectJobDone(@PathVariable Long jobId, @PathVariable Long requestId, @ApiIgnore OAuth2Authentication authentication){
        JobProjectSubmission request= jobContractService.rejectJob(jobId,requestId);
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

    @PostMapping(value="/v1/job/milestone/completed/submit/{id}", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> submitCompletedMilestone(@Valid @RequestParam(value = "data", required=true) String projectData, @PathVariable Long id,
                                                      @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles, @ApiIgnore OAuth2Authentication authentication) throws IOException {
        JobProjectSubmission response= jobContractService.submitCompletedMilestone(authentication, id,projectData,supportingFiles);
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

    @PutMapping("/v1/job/contract/end/{jobId}/{proposalId}/{userId}")
    public ResponseEntity<APIResponse> endContract(@RequestBody Rate rating, @PathVariable Long jobId, @PathVariable Long proposalId, @PathVariable Long userId, @RequestParam int state, @ApiIgnore OAuth2Authentication authentication){
        JobContract contract= jobContractService.endContract(authentication, rating, jobId,proposalId,userId,state);
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

    @GetMapping("/v1/job/contract/milestones/spent/amount/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse<Long>> findTotalMilestonesSpentAmountByProposalId(@Valid @PathVariable Long proposalId, @PathVariable Long jobId){
        Long amount=jobContractService.findTotalSpentAmountByProposalId(proposalId,jobId);
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
    public ResponseEntity<APIResponse> modifyMilestoneState(@PathVariable Long id, @RequestParam String status, @ApiIgnore OAuth2Authentication authentication){
        APIResponse response= jobContractService.modifyMilestoneState(authentication,id,status);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/v1/job/milestone/completed/approval/{id}")
    public ResponseEntity<APIResponse> approveCompletedMilestone(@Valid @RequestBody String request, @PathVariable Long id, @ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        APIResponse response= jobContractService.approveCompletedMilestone(authentication, id,request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/job/contract/{proposalId}/{jobId}")
    public ResponseEntity<APIResponse> findJobContractByProposalAndJobId(@Valid @PathVariable Long proposalId, @PathVariable Long jobId){
        JobContract response= jobContractService.findJobContractByProposalAndJobId(proposalId,jobId);
        if(response!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, response));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }

    @GetMapping("/v1/job/contract/milestone/{milestoneId}")
    public ResponseEntity<APIResponse> findMilestoneById(@Valid @PathVariable Long milestoneId){
        JobMilestone milestone= jobContractService.findMilestoneById(milestoneId);
        if(milestone!=null)
            return ResponseEntity.ok().body(new APIResponse("success",true, milestone));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false, null));
    }


    @GetMapping("/v1/bank/transfer/test")
    public ResponseEntity<APIResponse<String>> transferAmount() throws JsonProcessingException {

               JobTransfer transfer=new JobTransfer();

                transfer.setUserId(Long.valueOf(1));
                transfer.setJobId(Long.valueOf(1));
                transfer.setProposalId(Long.valueOf(1));
                transfer.setEmployerId(Long.valueOf(1));
                transfer.setCreatedAt(new Date());

                //transfer
                transfer.setAmount(18);
                transfer.setCurrency("NGN");
                transfer.setPaymentReference("j2hyewd798hsoqg2t8179qw8o");
                transfer.setInitBranchCode("682");

                //credit
                transfer.setCreditAccountName("DEDICATED NEFT O A");
                transfer.setCreditAccountNumber("0055982543");
                transfer.setCreditAccountBankCode("032");
                transfer.setCreditAccountBranchCode("682");
                transfer.setCreditAccountType("CASA");
                transfer.setCreditNarration("New Narration");

                //debit
                transfer.setDebitAccountName("OLANLOKUN LANRE A");
                transfer.setDebitAccountNumber("0040553624");
                transfer.setDebitAccountBranchCode("682");
                transfer.setDebitAccountType("CASA");
                transfer.setDebitNarration("New Naration");

                logger.info(new ObjectMapper().writeValueAsString(transfer));


        UBNFundsTransferResponse response= bankTransferService.transferUBNtoUBN(transfer);
        logger.info(response.toString());

        return ResponseEntity.ok().body(new APIResponse("success",true, response.getCode()));

    }
}
