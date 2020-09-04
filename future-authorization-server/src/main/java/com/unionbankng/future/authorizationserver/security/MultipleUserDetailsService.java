package com.unionbankng.future.authorizationserver.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MultipleUserDetailsService implements UserDetailsService {

    private List<UserDetailsService> userDetailsServiceList = new ArrayList<>();

    public MultipleUserDetailsService(UserDetailsService... userDetailsServices) {
        this.userDetailsServiceList.addAll(Arrays.asList(userDetailsServices));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        for (UserDetailsService userDetailsService : userDetailsServiceList) {
            try {
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);
               if(userDetails != null) {
                   return userDetails;
               }
            }
            catch (UsernameNotFoundException exception) {
                //log.debug("User not found  in {} trying next UserDetailsService", userDetailsService);
            }
        }

        throw new UsernameNotFoundException("Unable to find user " + username);
    }
}