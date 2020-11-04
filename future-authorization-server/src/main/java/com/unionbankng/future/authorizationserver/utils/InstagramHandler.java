package com.unionbankng.future.authorizationserver.utils;

import com.unionbankng.future.authorizationserver.pojos.LongLivedInstagramResponse;
import com.unionbankng.future.authorizationserver.pojos.ShortlivedInstagramResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class InstagramHandler {

    @Value("${instagram.sidekiq.client_id}")
    private String client_id;
    @Value("${instagram.sidekiq.client_secret}")
    private String client_secret;
    @Value("${instagram.sidekiq.grant_type}")
    private String grant_type;
    @Value("${instagram.sidekiq.redirect_uri}")
    private String redirect_uri;

    RestTemplate restTemplate = new RestTemplate();


    public ShortlivedInstagramResponse getShortLivedToken(String code){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id",client_id);
        params.add("client_secret",client_secret);
        params.add("grant_type",grant_type);
        params.add("redirect_uri",redirect_uri);
        params.add("code",code);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);


        try {

            ResponseEntity<ShortlivedInstagramResponse> shortlivedInstagramResponse = restTemplate.postForEntity
                    ("https://api.instagram.com/oauth/access_token", request, ShortlivedInstagramResponse.class);
            return shortlivedInstagramResponse.getBody();
        }catch (Exception e){
            return  null;
        }


    }

    public LongLivedInstagramResponse getLongLivedToken(String shortLivedToken){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token",shortLivedToken);
        params.add("client_secret",client_secret);
        params.add("grant_type","ig_exchange_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<LongLivedInstagramResponse> longLivedInstagramResponse = restTemplate.postForEntity
                    ("https://graph.instagram.com/access_token", request,LongLivedInstagramResponse.class);

            return longLivedInstagramResponse.getBody();

        }catch (Exception e){
            return null;
        }




    }

    public LongLivedInstagramResponse refreshLongLivedToken(String longLivedCurrency){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token",longLivedCurrency);
        params.add("grant_type","ig_refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<LongLivedInstagramResponse> longLivedInstagramResponse = restTemplate.postForEntity
                    ("https://graph.instagram.com/refresh_access_token", request,LongLivedInstagramResponse.class);

            return longLivedInstagramResponse.getBody();
        }catch (Exception e){
            return null;
        }



    }
}
