package com.unionbankng.future.authorizationserver.utils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public class Crypto {

    public static  void main(String args[]) throws JsonProcessingException {
        CryptoService cryptoService = new CryptoService();
        App app = new App();

        String data="{\"clientSecret\":\"KULAUSERCLIENT\", \"clientId\": \"KULAUSER\",\"grantType\":\"password\",\"username\":\"kulasuser\",\"password\":\"Fibba21\"}";
        String accessKey="KULAJLkwxFqcj5ojGqdjHdSYaFWdBprD";
        String cypher="bOLmaKWtD6Lu2WKhR146bNssy3YdrFZrKG3lv4nLS9AKaEcXEzPP++TvpOvYgIblcu17lilPIMVa+JLkwxFqcj5ojGqdjHdSYaFWdBprDSZAIwHOtZVdNOzugw5zbyZr8/UMe9wfZs09uVwWbcnNhkHN7qrQzM46lCJqJiOUEeQQq8ku1k9jW4AoeSVKSyh95uOfOZnTWk5bGHYdirglyg==";
        String encrypted= cryptoService.encrypt(data,accessKey);
//        Map<String, String> cypherO = app.getMapper().readValue(encrypted, Map.class);
//
        app.print(encrypted);

//        try {
//            String cypher=cryptoService.decrypt(encrypted, accessKey);
//
//            Map<String, String> cypherO = app.getMapper().readValue(cypher, Map.class);
//            System.out.println(cypherO.get("grantType"));
//        }catch (Exception ex){
//            app.print("Unable to decode cypher");
//            ex.printStackTrace();
//        }


    }
}
