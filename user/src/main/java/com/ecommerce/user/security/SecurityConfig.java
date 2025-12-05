package com.ecommerce.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.ecommerce.user.services.GitHubOAuth2UserService;

@Configuration
public class SecurityConfig {

	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	          .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/", "/public", "/css/**").permitAll()
	            .anyRequest().authenticated()
	          )
	          .oauth2Login(oauth2 -> oauth2
	            // default login page or custom login page can be set here
	            .loginPage("/oauth2/authorization/github")
	          )
	          .logout(logout -> logout
	            .logoutSuccessUrl("/")
	            .permitAll()
	          );
	        return http.build();
	    }
	 
	 @Bean
	 public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
	     return new GitHubOAuth2UserService();
	 }
}
