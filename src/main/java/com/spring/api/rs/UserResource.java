package com.spring.api.rs;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.api.rs.util.DefaultResource;
import com.spring.data.User;
import com.spring.db.service.UserService;

@RestController
@RequestMapping("users")
public class UserResource
    extends
        DefaultResource
{
    @Autowired
    private UserService userService;

    /** 
     * @return ResponseEntity<String>
     */
    @GetMapping
    public ResponseEntity<String> getUsers()
    {
        try
        {
            List<User> users = userService.getUsers();

            return ok( toJson( users ));
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /** 
     * @return ResponseEntity<String>
     */
    @GetMapping("{id}")
    public ResponseEntity<String> getUser( @PathVariable int userId )
    {
        try
        {
            User user = userService.getUser( userId );

            if( user == null )
            {
                return notFound("User not found for id: " + userId );
            }

            return ok( toJson( user ));
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /** 
     * @return ResponseEntity<String>
     */
    @GetMapping("admin")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<String> getAdmin()
    {
        try
        {
            return ok( "Admin" );
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
    @PostMapping
    public ResponseEntity<String> addUsers( @RequestBody User user )
    {
        try
        {
            user.setState( User.STATE_INACTIVE );

            userService.addUser( user );

            return created();
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }
}
