package com.spring.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.spring.data.User;

public class UserDetails
    implements
        org.springframework.security.core.userdetails.UserDetails
{
    private String userEmail;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetails( User user )
    {
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream()
                                        .map( role ->  new SimpleGrantedAuthority( role.getName() ))
                                        .collect( Collectors.toList() );
    }

    /** 
     * @return Collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return authorities;
    }

    /** 
     * @return String
     */
    @Override
    public String getPassword() 
    {
        return password;
    }

    /** 
     * @return String
     */
    @Override
    public String getUsername() 
    {
        return userEmail;
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() 
    {
        return true;
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() 
    {
        return true;
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() 
    {
        return true;
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isEnabled() 
    {
        return true;
    }
}
