package com.unionbankng.future.authorizationserver.interfaceimpl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.unionbankng.future.authorizationserver.controllers.RegistrationController;
import com.unionbankng.future.authorizationserver.interfaces.ThirdPartyOauthProvider;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ThirdPartyOauthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleOauthProvider implements ThirdPartyOauthProvider {

    private final Logger logger = LoggerFactory.getLogger(GoogleOauthProvider.class);
    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @Value("${sidekiq.google.client_id}")
    private String googleClientId;

    @Override
    public ThirdPartyOauthResponse authentcate(String idToken)  {

        GoogleIdToken googleIdToken = null;

        logger.error("Starting : {}",googleClientId);

        try {
            googleIdToken = getGoogleIdToken(idToken,googleClientId);

        } catch (GeneralSecurityException | IOException e) {
            logger.error("Google Auth error: {}",e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        logger.error("Here : {}",idToken);
        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        ThirdPartyOauthResponse thirdPartyOauthResponse = new ThirdPartyOauthResponse();
        thirdPartyOauthResponse.setEmail( payload.getEmail());
        thirdPartyOauthResponse.setFirstName((String) payload.get("given_name"));
        thirdPartyOauthResponse.setLastName((String) payload.get("family_name"));
        thirdPartyOauthResponse.setImage((String) payload.get("picture"));

        logger.error("Google Auth response: {}", thirdPartyOauthResponse);
        //Check if user exist
        return thirdPartyOauthResponse;
    }

    private GoogleIdToken getGoogleIdToken(String googleIdTokenString, String clientId) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        logger.error("I am here : {}");

            return verifier.verify(googleIdTokenString);

    }
}
