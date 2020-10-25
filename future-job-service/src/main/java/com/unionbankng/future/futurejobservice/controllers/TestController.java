package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.Test;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class TestController {

    private final TestService service;

    @GetMapping("/v1/test/list")
    public ResponseEntity<APIResponse>  getTests(){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,service.getTests()));

    }

    @PostMapping("/v1/test/add")
    public ResponseEntity<APIResponse> addTest(@Valid @RequestBody Test test){
        service.addTest(test);
        return ResponseEntity.ok().body(
                new APIResponse("success",true,test));

    }

}
