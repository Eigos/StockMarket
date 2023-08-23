package com.stockmarket.sproject.Security.Jwt.Dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenDto {

    @JsonProperty("issue_date")
    Date issueDate;

    @JsonProperty("expire_date")
    Date expirationDate;

    String token;

    String user;
}
