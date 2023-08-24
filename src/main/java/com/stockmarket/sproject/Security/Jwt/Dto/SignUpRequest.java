package com.stockmarket.sproject.Security.Jwt.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    
    @NotBlank(message = "Invalid Name: Name is NULL")
    @NotEmpty(message = "Invalid Name: Empty Name")
    @JsonProperty(value = "name")
    String name;

    
    @NotNull(message = "Invalid Last Name: Last Name is NULL")
    @NotEmpty(message = "Invalid Last Name: Empty Last Name ")
    @JsonProperty(value = "last_name")
    String lastName;

    @NotNull(message = "Invalid Email: Email is NULL")
    @NotEmpty(message = "Invalid Email: Empty Email")
    @Email(message = "Invalid Email: Please correct email")
    @JsonProperty(value = "email")
    String email;

    @NotNull(message = "Invalid Password: Password is NULL")
    @NotEmpty(message = "Invalid Password: Empty Password")
    @JsonProperty(value = "password")
    String password;

}
