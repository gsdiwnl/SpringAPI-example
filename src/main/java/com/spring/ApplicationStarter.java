package com.spring;

import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.data.User;
import com.spring.db.service.RoleService;
import com.spring.db.service.UserService;

@SpringBootApplication
public class ApplicationStarter 
{
	@Autowired
    private UserService userService;

	@Autowired
	private RoleService roleService;

    @PostConstruct
    public void initUsers() throws NoSuchAlgorithmException 
	{
		try
		{
			User user = userService.getUserByEmail("gd@interact.com.br");
	
			if( user == null )
			{
				user = new User();
				
				user.setName("Admin");
				user.setEmail("gd@interact.com.br");
				user.setPassword("psw");
				user.addRole( roleService.getUserRole() );
				user.addRole( roleService.getAdminRole() );
				
				userService.addUser( user );
			}
			
			User operator = userService.getUserByEmail("lk@interact.com.br");
			
			if( operator == null )
			{
				operator = new User();
				
				operator.setName("Operator");
				operator.setEmail("lk@interact.com.br");
				operator.setPassword("psw");
				operator.addRole( roleService.getUserRole() );
				
				userService.addUser( operator );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) 
	{
		SpringApplication.run( ApplicationStarter.class, args );
	}
}
