package com.unionbankng.future.authorizationserver.security;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{


	   private final AuthenticationManager authenticationManager;
	   private final DataSource dataSource;
	   private final PasswordEncoder passwordEncoder;
	   private final FutureDAOUserDetailsService futureDAOUserDetailsService;
	   
	   
	   @Override
	   public void configure (ClientDetailsServiceConfigurer clients) throws Exception {
	   clients.inMemory ()
	       .withClient ("web-client")
	               .authorizedGrantTypes ("password", "authorization_code", "refresh_token", "implicit")
	               .authorities ("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
	               .scopes ("read", "write")
	               .autoApprove (true)     
	               .secret (passwordEncoder. encode ("password")).and().withClient ("mobile-client");
	   }
	   
	    @Override
	    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	        security.tokenKeyAccess("permitAll()")
	                .checkTokenAccess("isAuthenticated()");
	    }
	   

	   
	   @Bean
	   @Primary
	   public DefaultTokenServices tokenServices() {
	       DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	       defaultTokenServices.setTokenStore(tokenStore());
	       defaultTokenServices.setSupportRefreshToken(true);
	       return defaultTokenServices;
	   }
	   
	   @Bean
	   public MultipleUserDetailsService multipleUserDetails() {
		   return new MultipleUserDetailsService(futureDAOUserDetailsService);
	   }
	   
	    @Override
	    public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints.authenticationManager (authenticationManager).tokenStore(tokenStore ());
	    }
	    
	    
	    @Bean
	    public TokenStore tokenStore () {
	    	 
	    	return new JdbcTokenStore(dataSource);
	     }
	    
	
}
