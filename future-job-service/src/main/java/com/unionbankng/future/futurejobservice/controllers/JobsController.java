package com.unionbankng.future.futurejobservice.controllers;

import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class JobsController {

    @GetMapping("/v1/get_jobs")
    public String getJobs() {
        return "Ok";
    }
}

