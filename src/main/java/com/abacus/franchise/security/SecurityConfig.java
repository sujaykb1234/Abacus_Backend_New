package com.abacus.franchise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
    private JwtFilter jwtAuthenticationFilter;

	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())

	        .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )

	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/abacus/v1/auth/**").permitAll()
	                .requestMatchers("/abacus/v1/users/**").permitAll()
	                .requestMatchers("/abacus/v1/franchise/**")
	                .hasAnyRole("FRANCHISE", "MASTER_FRANCHISE")
	                .requestMatchers("/abacus/v1/student/**")
	                .hasAnyRole("STUDENT")
	                .anyRequest().authenticated()
	        )

	        .exceptionHandling(exception -> exception
	                .authenticationEntryPoint(authenticationEntryPoint)
	                .accessDeniedHandler(accessDeniedHandler)
	        )

	        .addFilterBefore(jwtAuthenticationFilter,
	                UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//	    http
//	        .csrf().disable()
//	        .sessionManagement(session ->
//	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        )
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/abacus/v1/auth/**").permitAll()
//	            .requestMatchers("/abacus/v1/users/**").permitAll()
//	            .requestMatchers("/abacus/v1/franchise/**").hasAnyRole("FRANCHISE","MASTER_FRANCHISE")
//	            .anyRequest().authenticated()
//	        )
//	        .exceptionHandling(ex -> ex
//	                .authenticationEntryPoint(authenticationEntryPoint)
//	                .accessDeniedHandler(accessDeniedHandler)
//	        )
//	        .addFilterBefore(jwtAuthenticationFilter,
//	                UsernamePasswordAuthenticationFilter.class);
//
//	    return http.build();
//	}
}

//.hasAnyRole("FRANCHISE", "MASTER_FRANCHISE")

//.requestMatchers("/admin/**").hasRole("ADMIN")
//.requestMatchers("/student/**").hasRole("STUDENT")
