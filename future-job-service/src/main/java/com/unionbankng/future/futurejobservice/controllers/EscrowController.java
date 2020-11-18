package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.entities.EscrowRequest;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.EscrowService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class EscrowController {

    private final EscrowService escrowService;

    @ModelAttribute
    public void serResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,DELETE,PUT");
    }

    @PostMapping(value="/v1/escrow/add", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> addEscrowTransaction(@RequestBody EscrowRequest request){
        return ResponseEntity.ok().body(new APIResponse("success",true, escrowService.createTransaction(request)));
    }
}
