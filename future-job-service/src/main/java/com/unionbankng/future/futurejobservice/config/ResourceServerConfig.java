package com.unionbankng.future.futurejobservice.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Map;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${sidekiq.jwt.signing_key}")
	private String jwtSigningKey;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/api/v1/ping", "/api/v1/test", "/api/v1/notification/test", "/api/v1/jobs", "/api/v1/jobs/get_by_type/**", "/api/v1/jobs/get_by_type_and_category/**", "/api/v1/bundle/getbundles/**", "/api/v1/bundle/add", "/api/v1/bundle/delete/**").permitAll().and()
				.requestMatchers().antMatchers("/api/**").and().authorizeRequests().antMatchers("/api/**").authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices( createTokenServices() );
	}

	@Primary
	@Bean
	public DefaultTokenServices createTokenServices()  {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore( createTokenStore() );
		return defaultTokenServices;
	}

	@Bean
	public TokenStore createTokenStore(){
		return new JwtTokenStore( createJwtAccessTokenConverter() );
	}

	@Bean
	public JwtAccessTokenConverter createJwtAccessTokenConverter(){
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setAccessTokenConverter( new CustomAccessTokenConverter() );
		converter.setSigningKey(jwtSigningKey);
		return converter;
	}

	class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
		@Override
		public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
			OAuth2Authentication authentication
					= super.extractAuthentication(claims);
			authentication.setDetails(claims);
			return authentication;
		}
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://sidekiq-frontend.azurewebsites.net","https://kula.work"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "UPDATE", "DELETE"));
		corsConfiguration.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", corsConfiguration); // Global for all paths
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}