package com.abacus.franchise.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.access.secret}")
	private String accessSecret;
	
	 private Key accessKey;

	    @PostConstruct
	    public void init() {
	        this.accessKey = Keys.hmacShaKeyFor(
	            accessSecret.trim().getBytes(StandardCharsets.UTF_8)
	        );
	    }

	    private Key getAccessKey() {
	        return accessKey;
	    }

	    public String generateAccessToken(String username, String role, UUID userId) {
	        return Jwts.builder()
	                .setSubject(username)
	                .claim("role", role)
	                .claim("userId", userId.toString())
	                .setIssuedAt(new Date())
	                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }

	    public Claims extractAccessClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getAccessKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

    // ----------------- HELPERS -----------------
    public String getUsernameFromAccessToken(String token) {
        return extractAccessClaims(token).getSubject();
    }

    public String getRoleFromAccessToken(String token) {
        return extractAccessClaims(token).get("role", String.class);
    }
    
    public UUID getUserIdFromAccessToken(String token) {
        String userId = extractAccessClaims(token).get("userId", String.class);
        return UUID.fromString(userId);
    }
}
