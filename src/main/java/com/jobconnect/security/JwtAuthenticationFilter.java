package com.jobconnect.security;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(
	        jakarta.servlet.http.HttpServletRequest request,
	        jakarta.servlet.http.HttpServletResponse response,
	        jakarta.servlet.FilterChain filterChain)
	        throws jakarta.servlet.ServletException, java.io.IOException {
		
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
			String email = jwtService.extractEmail(token);
			System.out.println("JWT EMAIL = " + email);
			String role = jwtService.extractRole(token);
			System.out.println("ROLE FROM TOKEN = " + role);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("AUTHENTICATION SET = " +
			SecurityContextHolder.getContext().getAuthentication());
		
		} catch(Exception e) {
		

	    filterChain.doFilter(request, response);
	    return;
	}
		}
		
			
			filterChain.doFilter(request, response);
		
	}
}	

