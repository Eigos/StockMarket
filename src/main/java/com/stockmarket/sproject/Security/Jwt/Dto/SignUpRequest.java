package com.stockmarket.sproject.Security.Jwt.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    String name;

    @JsonProperty(value = "last_name")
    String lastName;

    @NotNull
    @Email
    String email;

    @NotNull
    String password;

}
