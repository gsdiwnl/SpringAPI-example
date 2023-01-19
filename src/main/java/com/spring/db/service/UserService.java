package com.spring.db.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.data.User;
import com.spring.db.repository.UserRepository;
import com.spring.security.AuthenticateService;

@Service
public class UserService 
{
    @Autowired
    private UserRepository userRepository;

    public UserService()
    {
    }
    
    /**
     * @param user User
     * @throws NoSuchAlgorithmException
     */
    public void addUser( User user ) throws NoSuchAlgorithmException
    {
        user.setPassword( AuthenticateService.hash( user.getPassword() ));
        userRepository.save( user );
    }

    /**
     * @param userId int
     * @return User
     */
    public User getUser( int userId )
    {
        return userRepository.findById( userId ).orElse( null );
    }

    /**
     * @param email String
     * @return User
     */
    public User getUserByEmail( String email )
    {
        return userRepository.findByEmail( email );
    } 
    
    /**
     * @return  List<User>
     */
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }
}
