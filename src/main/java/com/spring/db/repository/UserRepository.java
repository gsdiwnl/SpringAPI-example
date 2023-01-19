package com.spring.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.data.User;

public interface UserRepository
    extends 
        JpaRepository<User, Integer>
{
    @Query( value = "SELECT * FROM users WHERE email = :email", nativeQuery = true )
    public User findByEmail( @Param("email") String email );
}
