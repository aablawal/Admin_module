package com.unionbankng.future.authorizationserver.utils;

import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Oauth2Utils {

    /**
     * Extracts the email id from user attributes received from OAuth2 provider, e.g. Google
     *
     */
    public String getOAuth2Email(String registrationId, Map<String, Object> attributes) {

        return (String) attributes.get(StandardClaimNames.EMAIL);
    }

    /**
     * Checks if the account at the OAuth2 provider is verified
     */
    public boolean getOAuth2AccountVerified(String registrationId, Map<String, Object> attributes) {

        Object verified = attributes.get(StandardClaimNames.EMAIL_VERIFIED);
        if (verified == null)
            verified = attributes.get("verified");

        return (boolean) verified;
    }
}
