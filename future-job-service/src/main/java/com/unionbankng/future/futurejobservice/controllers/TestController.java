package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.Job;
import com.unionbankng.future.futurejobservice.entities.Test;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.JobService;
import com.unionbankng.future.futurejobservice.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping("/v1/test")
    public List<Test> test() {
        return service.getTests();
    }

    @PostMapping("/v1/test/add")
    public Test add(@RequestBody Test test) {
         service.addTest(test);
         return  test;
    }

    @PostMapping("/v1/test/add1")
    public Job addJob(@RequestBody Job data){
       service.addDataJob(data);
       return  data;
    }



    @PostMapping("/v1/test/val")
    public String val(@RequestBody Test test) {
        return  "Completed";
    }
}