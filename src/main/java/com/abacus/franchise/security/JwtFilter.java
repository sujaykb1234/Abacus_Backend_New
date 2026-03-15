package com.abacus.franchise.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
    	
    	String authHeader = request.getHeader("Authorization");

    	if (authHeader != null && authHeader.startsWith("Bearer ")) {
    	    try {
    	        String token = authHeader.substring(7).trim(); // ✅ IMPORTANT

    	        System.out.println("TOKEN = [" + token + "]");

    	        Claims claims = jwtUtil.extractAccessClaims(token);

    	        String username = claims.getSubject();
    	        String role = claims.get("role", String.class);
    	        UUID userId = UUID.fromString(claims.get("userId", String.class));

    	        List<GrantedAuthority> authorities =
    	                List.of(new SimpleGrantedAuthority("ROLE_" + role));

    	        UsernamePasswordAuthenticationToken authToken =
    	                new UsernamePasswordAuthenticationToken(
    	                        username,
    	                        null,
    	                        authorities
    	                );

    	        request.setAttribute("userId", userId);
    	        request.setAttribute("role", role);

    	        SecurityContextHolder.getContext().setAuthentication(authToken);

    	    } catch (ExpiredJwtException ex) {
    	        throw ex;
    	    }
    	    catch (MalformedJwtException | SignatureException ex) {
    	        throw ex;
    	    }
    	}

    	filterChain.doFilter(request, response);    
    	
    }
    


}
