package com.abacus.franchise.security;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.abacus.franchise.response.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        SuccessResponse res = new SuccessResponse();
        res.setStatus(false);
        res.setMessage("ACCESS DENIED");
        res.setStatusCode(HttpStatusCode.valueOf(403));
        res.setResponse(null);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }
}