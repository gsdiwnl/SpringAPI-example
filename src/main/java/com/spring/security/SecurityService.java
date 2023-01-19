package com.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.api.rs.util.RequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( 
    prePostEnabled = false,
    securedEnabled = false,
    jsr250Enabled = true )
public class SecurityService
    extends
        WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RequestFilter requestFilter;

    /** 
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception 
    {
        auth.userDetailsService( userDetailsService );
    }

    /** 
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure( HttpSecurity httpSecurity ) throws Exception 
    {
        httpSecurity.csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/authenticate", "/authenticate/google", "/signup" )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and().exceptionHandling().authenticationEntryPoint( new AuthenticationEntryPoint() )
                    .and().sessionManagement()
                    .sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        httpSecurity.addFilterBefore( requestFilter, UsernamePasswordAuthenticationFilter.class );
    }

    /** 
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean( name = BeanIds.AUTHENTICATION_MANAGER )
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception 
    {
        return super.authenticationManagerBean();
    }
    
    /** 
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}
