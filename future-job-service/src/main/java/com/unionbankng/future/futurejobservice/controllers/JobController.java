package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobProposalService;
import com.unionbankng.future.futurejobservice.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobController {

    @ModelAttribute
    public void serResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
    }
    private final JobService service;

    @PostMapping(value="/v1/job/add", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> addJob(@Valid @RequestParam(value = "data", required=true) String jobData,
                                              @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles,
                                              @RequestParam(value = "ndaFiles", required = false) MultipartFile[] ndaFiles) throws IOException{

        Job addedJob=service.addJob(jobData,supportingFiles,ndaFiles);
        if(addedJob!=null)
          return ResponseEntity.ok().body(new APIResponse("success",true,addedJob));
        else
            return ResponseEntity.ok().body(new APIResponse("failed",false,null));
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

    @GetMapping("/v1/jobs/owner/{oid}")
    public ResponseEntity<APIResponse> getJobsByOwnerId(@PathVariable Long oid,@RequestParam int page, @RequestParam int size, Model model){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.findJobsByOwnerId(oid,PageRequest.of(page,size), model)));
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

}
