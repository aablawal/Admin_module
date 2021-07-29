package com.unionbankng.future.futuremessagingservice.config;

import com.unionbankng.future.futuremessagingservice.pojos.UbnResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

@RefreshScope
@Component
public class UBNConfigurationProperties {

    private static String AUTH_URL ;
    public static String SEND_SMS ;
    public static String EMAIL_URL;
    private static String CLIENT_SECRET;
    private static String CLIENT_ID;
    private static String GRANT_TYPE;
    private static String USERNAME;
    private static String PASSWORD;

    public static String SMS_GL;

    @Value("${sms.gl.number}")
    public void setSmsGl(String smsGl){
        SMS_GL = smsGl;
    }

    @Value("${unionbankng.base.url}")
    public void setEndpoints(String baseUrl) {
        AUTH_URL = baseUrl+"authserv/oauth/token";
        SEND_SMS = baseUrl+"SendSmsAPI/SendSMSRS";
        EMAIL_URL = baseUrl+"emailservice-api/secured/sendemail";
    }


    @Value("#{${unionbankng.credentials}}")
    public void setCredentials(Map<String, String> credentials) {
        CLIENT_SECRET = credentials.get("clientSecret");
        CLIENT_ID = credentials.get("clientId");
        GRANT_TYPE = credentials.get("grantType");
        USERNAME = credentials.get("username");
        PASSWORD = credentials.get("password");
    }

    public static Client ignoreSSLClient() throws Exception {


        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{new X509TrustManager()
        {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException{}
            public X509Certificate[] getAcceptedIssuers()
            {
                return new X509Certificate[0];
            }

        }}, new java.security.SecureRandom());


        HostnameVerifier allowAll = new HostnameVerifier()
        {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }

        };

        return ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier(allowAll).build();

    }

    public static String getAccountAccessToken() throws Exception {

        Client client = ignoreSSLClient();
        WebTarget target = client.target(AUTH_URL);

        target = target.queryParam("username", USERNAME).queryParam("password", PASSWORD)
                .queryParam("client_secret", CLIENT_SECRET).queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(new UbnResponse(), MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200)
            return response.readEntity(UbnResponse.class).getAccess_token();


        return null;


    }
}
