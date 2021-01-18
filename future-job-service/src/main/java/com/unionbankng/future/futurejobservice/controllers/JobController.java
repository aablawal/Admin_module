package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.*;
import com.unionbankng.future.futurejobservice.repositories.JobRepository;
import com.unionbankng.future.futurejobservice.services.EmailService;
import com.unionbankng.future.futurejobservice.services.JobService;
import com.unionbankng.future.futurejobservice.services.UserService;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobController {

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT");
    }
    private final JobService service;
    private final NotificationSender notificationSender;
    private final UserService userService;
    private final JobRepository jobRepository;
    Logger logger = LoggerFactory.getLogger(JobController.class);

    @PostMapping(value="/v1/job/add", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> addJob(@Valid @RequestParam(value = "data", required=true) String jobData,
                                              @RequestParam(value = "team", required=true) String teamData,
                                              @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles,
                                              @RequestParam(value = "ndaFiles", required = false) MultipartFile[] ndaFiles) throws IOException{

        Job addedJob=service.addJob(jobData,teamData,supportingFiles,ndaFiles);
        if(addedJob!=null)
          return ResponseEntity.ok().body(new APIResponse("success",true,addedJob));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false,null));
    }

    @PutMapping("/v1/job/close")
    public  ResponseEntity<APIResponse> closeJobById(@RequestParam Long id, @RequestParam int state){
        Job job=service.closeJobById(id,state);
        if(job!=null)
           return ResponseEntity.ok().body(new APIResponse("Job closed successful",true,job));
        else
            return ResponseEntity.ok().body(new APIResponse("Unable to close the job",false,null));
    }

    @PutMapping("/v1/job/open")
    public  ResponseEntity<APIResponse> openJobById(@RequestParam Long id){
        Job job=service.openJobById(id);
        if(job!=null)
            return ResponseEntity.ok().body(new APIResponse("Job opened successful",true,job));
        else
            return ResponseEntity.ok().body(new APIResponse("Unable to open the job",false,null));
    }

    @PutMapping("/v1/job/repeat")
    public  ResponseEntity<APIResponse> repeatJobById(@RequestParam Long id){
        Job job=service.repeatJobById(id);
        if(job!=null)
            return ResponseEntity.ok().body(new APIResponse("Job repeated successful",true,job));
        else
            return ResponseEntity.ok().body(new APIResponse("Unable to repeat the job",false,null));
    }

    @DeleteMapping("/v1/job/delete/{id}")
    public ResponseEntity<APIResponse> deleteJob(@PathVariable  Long id){
        service.deleteJobById(id);
        return ResponseEntity.ok().body(new APIResponse("Job deleted successful",true,null));
    }

    @GetMapping("/v1/job/{id}")
    public ResponseEntity<APIResponse> getJobById(@PathVariable Long id, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobById(id,model)));
    }

    @GetMapping("/v1/job/invitation/{invitationId}")
    public ResponseEntity<APIResponse> getJobByInvitationId(@PathVariable String invitationId, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.getJobByInvitationId(invitationId,model)));
    }

    @GetMapping("/v1/jobs/owner/{oid}")
    public ResponseEntity<APIResponse> getJobsByOwnerId(@PathVariable Long oid,@RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobsByOwnerId(oid,PageRequest.of(page,size), model)));
    }
    @GetMapping("/v1/user/jobs/status/{uid}")
    public ResponseEntity<APIResponse> getJobsByUserIdAndStatus(@PathVariable Long uid,@RequestParam String status, @RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobsByUserIdAndStatus(uid,status,PageRequest.of(page,size), model)));
    }
    @GetMapping("/v1/my-job/list/{oid}")
    public ResponseEntity<APIResponse> getJobsByOwnerIdAndStatus(@PathVariable Long oid,@RequestParam String status, @RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobsByOwnerIdAndStatus(oid,status,PageRequest.of(page,size), model)));
    }
    @GetMapping("/v1/jobs/type/{type}")
    public ResponseEntity<APIResponse<Model>> getJobsByOwnerId(@PathVariable String type,@RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobsByType(JobType.valueOf(type.toUpperCase()),PageRequest.of(page,size), model)));
    }

    @GetMapping("/v1/jobs")
    public ResponseEntity<APIResponse<Model>> getAnyJob(@RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.getJobs(PageRequest.of(page,size), model)));
    }

    @GetMapping("/v1/test")
    public ResponseEntity<APIResponse<String>> test(@ApiIgnore OAuth2Authentication authentication){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        return ResponseEntity.ok().body(
                new APIResponse("success",true, jwtUserDetail));
    }
}
