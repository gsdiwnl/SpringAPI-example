package com.spring.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuth 
{
    private String userEmail;
    private String password;
}
