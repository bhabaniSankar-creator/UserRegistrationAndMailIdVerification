package com.hitu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hitu.filter.JwtAuthFilter;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	@SneakyThrows
	public AuthenticationManager authManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(encoder);
		authProvider.setUserDetailsService(userDetailsService);
		return authProvider;
	}
	

	@Bean
	@SneakyThrows
	public SecurityFilterChain security(HttpSecurity http,JwtAuthFilter jwtAuthFilter) {

		http.csrf(csrf -> csrf.disable()) // Disabling CSRF protection
		.authorizeHttpRequests(
				auth -> auth.requestMatchers("/api/register", "/api/login", "/api/verify").permitAll()
				
								/* .requestMatchers(HttpMethod.PUT,"/{userId}").hasRole("SUPERADMIN") */
				.requestMatchers(HttpMethod.PUT,"/api").authenticated()

								/*
								 * .requestMatchers(HttpMethod.DELETE, "/{userId}").hasRole("SUPERADMIN")
								 * .requestMatchers(HttpMethod.DELETE).hasRole("SUPERADMIN")
								 */

				.requestMatchers(HttpMethod.GET, "api/{userId}").hasAnyRole("SUPERADMIN","ADMIN")
				.anyRequest().hasRole("SUPERADMIN"))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
