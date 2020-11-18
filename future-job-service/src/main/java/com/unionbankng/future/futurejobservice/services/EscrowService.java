package com.unionbankng.future.futurejobservice.services;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Service
@NoArgsConstructor
public class EscrowService {

    @Value("${sidekiq.escrow.appId}")
    private int  appId;

    @Value("${sidekiq.escrow.token}")
    private String token;

    @Value("${sidekiq.escrow.baseUrl}")
    private String baseURL="";


    @Autowired
   private RestTemplate rest;

   public HttpHeaders getHeaders(){
       HttpHeaders headers = new HttpHeaders();
       headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
       headers.setContentType(MediaType.MULTIPART_FORM_DATA);
       headers.setCacheControl("no-cache");
       headers.add("Token",token);
       return  headers;
   }

   public  String getTransactions(String status){
       HttpEntity<String> entity = new HttpEntity<String>(this.getHeaders());
       return rest.exchange(baseURL+"/Transaction/AppTranx?appid="+appId+"&action="+status, HttpMethod.GET, entity, String.class).getBody();
   }
}
