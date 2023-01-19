package com.spring.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.data.Role;
import com.spring.db.repository.RoleRepository;

@Service
public class RoleService         
{
    @Autowired
    private RoleRepository roleRepository;
    
    /** 
     * @param role Role
     */
    public void addRole( Role role )
    {
        roleRepository.save( role );
    }

    /** 
     * @return Role
     */
    public Role getUserRole()
    {
        Role role = roleRepository.findByName( Role.ROLE_USER );

        if( role == null )
        {
            role = roleRepository.save( new Role( Role.ROLE_USER ));
        }

        return role;
    }

    /** 
     * @return Role
     */
    public Role getAdminRole()
    {
        Role role = roleRepository.findByName( Role.ROLE_ADMIN );

        if( role == null )
        {
            role = roleRepository.save( new Role( Role.ROLE_ADMIN ));
        }

        return role;
    }
}
