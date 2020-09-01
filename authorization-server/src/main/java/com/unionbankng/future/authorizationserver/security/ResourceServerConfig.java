package com.unionbankng.future.authorizationserver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
	public void configure(HttpSecurity http) throws Exception {

    	http.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**").permitAll()
				.and().requestMatchers().antMatchers("/api/**")
				.and().authorizeRequests()
				.antMatchers("/api/**").authenticated();
    }
}