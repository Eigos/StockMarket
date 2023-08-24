package com.stockmarket.sproject.Security.Jwt.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {

    @NotNull(message = "Invalid Username: Username is NULL")
    @NotEmpty(message = "Invalid Username: Empty Username")
    private String username;

    
    @NotNull(message = "Invalid Password: Password is NULL")
    @NotEmpty(message = "Invalid Password: Empty Password")
    private String password;

}
