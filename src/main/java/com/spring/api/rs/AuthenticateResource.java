package com.spring.api.rs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spring.api.rs.util.DefaultResource;
import com.spring.data.User;
import com.spring.db.service.RoleService;
import com.spring.db.service.UserService;
import com.spring.misc.NotFoundException;
import com.spring.misc.UnauthorizedException;
import com.spring.security.AuthenticateService;
import com.spring.security.UserAuth;

@RestController
@RequestMapping("authenticate")
public class AuthenticateResource
    extends
        DefaultResource
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authorizationManager;
    
    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private Gson gson;

    /**
     * @param userEmail String
     * @param password String
     * @return ResponseEntity<String>
     */
    @PostMapping("")
    public ResponseEntity<String> authenticate( @RequestBody UserAuth userAuth )
    {
        try
        {
            try
            {
                userAuth.setPassword( AuthenticateService.hash( userAuth.getPassword() ));

                authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( userAuth.getUserEmail(), userAuth.getPassword() )
                );

                return ok( authenticateService.createToken( userAuth.getUserEmail() ));
            }
            catch( NotFoundException e )
            {
                return notFound( e );
            }
            catch( BadCredentialsException e )
            {
                return unauthorizedError("Incorrect password");
            }
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /** 
     * @param user
     * @return ResponseEntity<String>
     */
    @GetMapping("google")
    public ResponseEntity<String> googleAuthorize( @RequestParam String token )
    {
        try
        {
            try
            {
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("https://www.googleapis.com/oauth2/v1/userinfo");
                Response response = target.request().header( "Authorization", "Bearer " + token ).get();
                String content = response.readEntity( String.class );
    
                JsonObject userInfo = gson.fromJson( content, JsonObject.class );
                
                boolean isVerified = userInfo.get("verified_email").getAsBoolean();
                
                if( !isVerified )
                {
                    throw new UnauthorizedException("Email not verified");
                }
                
                String email = userInfo.get("email").getAsString();
                String name = userInfo.get("name").getAsString();
                String id = userInfo.get("id").getAsString();
                
                User user = userService.getUserByEmail( email );
    
                if( user == null )
                {
                    user = new User();
                    
                    user.setEmail( email );
                    user.setName( name );
                    user.setState( User.STATE_ACTIVE );
                    user.setPassword( AuthenticateService.hash( id ));
                    user.addRole( roleService.getUserRole() );
                    
                    userService.addUser( user );
                }
    
                return ok( gson.toJson( token ));
            }
            catch( UnauthorizedException e )
            {
                return unauthorizedError( e );
            }
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }
}
