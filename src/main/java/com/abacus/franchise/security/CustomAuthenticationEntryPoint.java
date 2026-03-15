package com.abacus.franchise.security;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.abacus.franchise.response.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        SuccessResponse res = new SuccessResponse();
        res.setStatus(false);
        res.setMessage("UNAUTHORIZED: INVALID TOKEN");
        res.setStatusCode(HttpStatusCode.valueOf(401));
        res.setResponse(null);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }
}
