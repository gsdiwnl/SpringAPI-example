package com.spring.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.data.Role;

public interface RoleRepository 
    extends 
        JpaRepository<Role, Integer>
{
    @Query( value = "SELECT * FROM roles WHERE name = :name", nativeQuery = true )
    public Role findByName( @Param("name") String name );
}
