package com.unionbankng.future.authorizationserver.utils;

public class Crypto {

    public static void main(String args[]) {

        AESCryptoService cryptoService = new AESCryptoService();
        String text=cryptoService.decrypt("U2FsdGVkX18EcieX57sz91yu8tHM89oOmLQp5j7eCPA=","Gh79j96-6762-493c-837949");
        System.out.print(text);

    }
}
