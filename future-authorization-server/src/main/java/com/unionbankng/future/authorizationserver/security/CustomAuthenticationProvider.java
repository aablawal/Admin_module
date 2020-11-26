package com.unionbankng.future.authorizationserver.security;

import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.interfaceimpl.GoogleOauthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Map;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private GoogleOauthProvider googleOauthProvider;


    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        String thirdPartyOauthToken = "";
        if (!(auth.getDetails() instanceof WebAuthenticationDetails)) {
            Map<String, String> map = (Map<String, String>) auth.getDetails();
            thirdPartyOauthToken = map.get("oauth_token");
        }

        this.logger.info(String.format("token is : %s",thirdPartyOauthToken));
        String username = auth.getPrincipal() == null ? "NONE_PROVIDED" : auth.getName();
        boolean cacheWasUsed = true;
        FutureDAOUserDetails user = (FutureDAOUserDetails)this.getUserCache().getUserFromCache(username);

        if(user == null) {
            cacheWasUsed =false;
            user = (FutureDAOUserDetails) this.retrieveUser(auth.getName(),
                    (UsernamePasswordAuthenticationToken) auth);

        }

        this.getPreAuthenticationChecks().check(user);

        if(user.getAuthProvider().equals(AuthProvider.EMAIL)) {


            if(auth.getCredentials() == null && thirdPartyOauthToken != null)
                throw new BadCredentialsException(this.messages.getMessage("wrong.authprovider.email.error", "Bad credentials"));
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) auth);
        }

        if(user.getAuthProvider().equals(AuthProvider.GOOGLE)) {

            if(thirdPartyOauthToken == null)
                throw new BadCredentialsException(this.messages.getMessage("wrong.authprovider.google.error", "Bad credentials"));

            this.logger.info("Using google");
            googleOauthProvider.authentcate(thirdPartyOauthToken);
        }


        if (!cacheWasUsed) {
            this.getUserCache().putUserInCache(user);
        }

        this.getPostAuthenticationChecks().check(user);

        Object principalToReturn = user;
        if (this.isForcePrincipalAsString()) {
            principalToReturn = user.getUsername();
        }

        return this.createSuccessAuthentication(principalToReturn, auth, user);


    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

}
