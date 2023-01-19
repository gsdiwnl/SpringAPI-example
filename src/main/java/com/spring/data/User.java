package com.spring.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User 
{
    public static final String[] STATES = new String[] 
    {
        "Inactive",
        "Active"
    };

    public static final int STATE_INACTIVE = 0;
    public static final int STATE_ACTIVE = 1;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    private int state;
    
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable( name = "users_roles",
                joinColumns = @JoinColumn( name = "ref_user"),
                inverseJoinColumns = @JoinColumn( name = "ref_role" ))
    private List<Role> roles = new ArrayList<Role>();

    public void addRole( Role role )
    {
        this.roles.add( role );
    }
}