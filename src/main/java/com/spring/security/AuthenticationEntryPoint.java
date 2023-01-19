package com.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import com.spring.misc.Error;

public class AuthenticationEntryPoint 
    implements org.springframework.security.web.AuthenticationEntryPoint
{

    @Override
    public void commence( HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authException ) throws IOException, ServletException 
    {
        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
        response.getWriter().write( new Error( HttpStatus.UNAUTHORIZED, "Invalid authorization token").toJson() );
    }
}
