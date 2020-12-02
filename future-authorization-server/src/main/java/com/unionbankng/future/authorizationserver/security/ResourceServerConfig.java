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

    	http.cors().and().csrf().disable().authorizeRequests().antMatchers("/login","/oauth/token", "/oauth/authorize/**","/api/v1/registration/**",
				"/api/v1/security/**","/api/v1/tags/find_by_type","/api/v1/user_interest/add_for_user","/api/v1/tags/find_by_type_likely_name","/api/v1/tags/create").permitAll()
				.and().requestMatchers().antMatchers("/api/**")
				.and().authorizeRequests()
				.antMatchers("/api/**").authenticated();
    }
}