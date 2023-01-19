package com.spring.api.rs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.AuthenticateService;
import com.spring.security.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter
    extends
        OncePerRequestFilter 
{
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private UserDetailsService userDetailsService;

    /** 
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain ) throws ServletException, IOException 
    {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userEmail = null;

        if( authorizationHeader != null ) 
        {
            token = authorizationHeader.replace("Bearer ", "");
            userEmail = authenticateService.verifyToken( token );
        }

        if( userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername( userEmail );

            if( authenticateService.verifyUserDetails( userEmail, userDetails ))
            {
                setAuthentication( httpServletRequest, userDetails );
            }
        }
        
        httpServletResponse.addHeader( "Access-Control-Allow-Origin", "*" );
        filterChain.doFilter( httpServletRequest, httpServletResponse );
    }

    /** 
     * @param request
     * @param userDetails
     */
    private void setAuthentication( HttpServletRequest request, UserDetails userDetails )
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
        usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ));
        SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
    }
}
