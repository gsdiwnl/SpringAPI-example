package com.spring.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthenticateService 
{
    public static final int TOKEN_EXPIRED = 600_000;
    public static final String TOKEN_SALT = "66B43598-B1C1-4690-BE47-77FA67C18FE1";

    /** 
     * @param claims
     * @param userEmail
     * @return String
     */
    public String createToken( String userEmail )
    {
        return JWT.create()
                .withSubject( userEmail )
                .withExpiresAt( new Date( System.currentTimeMillis() + TOKEN_EXPIRED ))
                .sign( Algorithm.HMAC512( TOKEN_SALT ));
    }

    /**
     * @param token String
     * @return String
     */
    public String verifyToken( String token )
    {
        String userEmail = JWT.require( Algorithm.HMAC512( TOKEN_SALT ))
                            .build()
                            .verify( token )
                            .getSubject();

        return userEmail;
    }

    /** 
     * @param userEmail
     * @param userDetails
     * @return boolean
     */
    public boolean verifyUserDetails( String userEmail, UserDetails userDetails )
    {
        return userEmail.equals( userDetails.getUsername() );
    }

    /** 
     * @param password
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public static String hash( String password ) throws NoSuchAlgorithmException
    {
        return "<" + String.format( "%040x", new BigInteger( MessageDigest.getInstance( "SHA1" ).digest( password.getBytes() ) ).abs() ) + ">";
    }
}
