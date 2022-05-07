package com.unionbankng.future.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {


    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "Hello World";
    }

}
