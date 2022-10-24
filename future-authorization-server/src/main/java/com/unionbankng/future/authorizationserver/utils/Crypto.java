package com.unionbankng.future.authorizationserver.utils;

public class Crypto {


    public static void main(String args[]){
        CryptoService cryptoService = new CryptoService();
        String text=cryptoService.decryptAES("U2FsdGVkX1/S9nkVnKdksH3KWY9ABs//syV+5AECbHg=","Gh79j96-6762-493c-837949");

        System.out.println(text);
    }
}
