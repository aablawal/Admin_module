package com.unionbankng.future.learn.pojo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Data
public class UploadFileFromURLResponse {
    @NotNull
    private String fileName;
    @NotNull
    private MultipartFile file;
}
