package com.unionbankng.future.futureloanservice.controllers;

import com.unionbankng.future.futureloanservice.pojos.APIResponse;
import com.unionbankng.future.futureloanservice.services.ChatFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class ChatFileController {

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT");
    }
    private final ChatFileUploadService chatFileUploadService;

    @PostMapping(value="/v1/upload/chat_file", consumes="multipart/form-data")
    public ResponseEntity<APIResponse> uploadFile(@Valid @RequestParam(value = "details") String details,
                                              @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        return ResponseEntity.ok().body(chatFileUploadService.uploadChatFiles(details,files));
    }

}
