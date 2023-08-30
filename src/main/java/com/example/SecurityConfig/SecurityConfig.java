package com.example.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.authorizeHttpRequests( auth -> {
					auth.requestMatchers("/v1/index2").permitAll();
					auth.anyRequest().authenticated();
				})
				.formLogin()
					.successHandler(successHandler()) //url a donde se va a ir después de iniciar sesión
					.permitAll()
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //	ALWAYS - IF_REQUIRED - NEVER - STATELESS
					.invalidSessionUrl("/login")
					.maximumSessions(1)
					.expiredUrl("/login")
					.sessionRegistry(sessionRegistry())
				.and()
				.sessionFixation() //ataque de fijación de sesión
					.migrateSession() //genera otro id de sesión -newSession - none
				.and()
				.httpBasic()
				.and()
				.build();
	}

	@Bean
	public SessionRegistry sessionRegistry(){
		return new SessionRegistryImpl();
	}

	public AuthenticationSuccessHandler successHandler(){
		return ((request, response, authentication) -> {
			response.sendRedirect("/v1/session");
		});
	}

}
