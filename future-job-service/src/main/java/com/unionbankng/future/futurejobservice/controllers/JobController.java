package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.enums.JobStatus;
import com.unionbankng.future.futurejobservice.enums.JobType;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


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
                                              @RequestParam(value = "supporting_files", required = false) MultipartFile[] supporting_files,
                                              @RequestParam(value = "nda_files", required = false) MultipartFile[] nda_files) throws IOException{

        Job addedJob=service.addJob(jobData,supporting_files,nda_files);
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
    public ResponseEntity<APIResponse> getJobById(@PathVariable Long id){
        Job job = service.findJobById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return ResponseEntity.ok().body(
                new APIResponse("success",true,job));
    }

    @GetMapping("/v1/jobs/owner/{oid}")
    public ResponseEntity<APIResponse> getJobsByOwnerId(@PathVariable Long oid){
        List<Job> job = service.findJobsByOwnerId(oid).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No job found"));
        return ResponseEntity.ok().body(
                new APIResponse("success",true,job));
    }
    @GetMapping("/v1/jobs/type/{type}")
    public ResponseEntity<APIResponse> getJobsByOwnerId(@PathVariable String type){
        List<Job> job = service.findJobByType(JobType.valueOf(type.toUpperCase())).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No job found"));
        return ResponseEntity.ok().body(
                new APIResponse("success",true,job));
    }

    @GetMapping("/v1/jobs/status/{status}")
    public ResponseEntity<APIResponse> getJobsByStatus(@PathVariable String status){
        List<Job> job = service.findJobsByStatus(JobStatus.valueOf(status.toUpperCase())).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No job found"));
        return ResponseEntity.ok().body(
                new APIResponse("success",true,job));
    }

    @GetMapping("/v1/jobs")
    public ResponseEntity<APIResponse> getAnyJob(){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.getJobs()));
    }
//    @GetMapping("/v1/jobs/search?={oid}")
//    public ResponseEntity<APIResponse> getJobsBySearch(@PathVariable String search){
//        List<Job> job = service.findJobsByOwnerId(oid).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No job found"));
//        return ResponseEntity.ok().body(
//                new APIResponse("success",true,job));
//    }


}
