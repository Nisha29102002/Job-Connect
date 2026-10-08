package com.jobconnect.config;
import com.jobconnect.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.jobconnect.security.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(
	        HttpSecurity http,
	        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())
	        .formLogin(form -> form.disable())
	        .httpBasic(basic -> basic.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/login","/users").permitAll()
	            .requestMatchers(HttpMethod.POST,"/jobs").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.PUT, "/jobs").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.POST,"/companies").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.PUT, "/companies/**").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.DELETE, "/companies/**").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.PUT, "/applications/*/status").hasRole("RECRUITER")
	            .requestMatchers("/recruiter/**").hasRole("RECRUITER")
	            .requestMatchers(HttpMethod.POST, "/resumes/upload").authenticated()
	            .requestMatchers("/error").permitAll()
	            
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(
	            jwtAuthenticationFilter,
	            UsernamePasswordAuthenticationFilter.class
	        );

	    return http.build();
	}

}
