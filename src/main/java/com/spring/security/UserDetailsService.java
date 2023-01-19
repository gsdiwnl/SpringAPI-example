package com.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.db.service.UserService;

@Service
public class UserDetailsService 
    implements 
        org.springframework.security.core.userdetails.UserDetailsService
{
    @Autowired
    private UserService userService;

    /** 
     * @param userEmail String
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername( String userEmail ) throws UsernameNotFoundException 
    {
        com.spring.data.User user = userService.getUserByEmail( userEmail );

        return new com.spring.security.UserDetails( user );
    }
}
