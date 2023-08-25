package com.stockmarket.sproject.application.dto;

import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockmarket.sproject.Security.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    
    private static final long ID_BOUND_MIN = 1L;
    private static final long ID_BOUND_MAX = Integer.MAX_VALUE;

    @NotNull(message = "Invalid User ID: User ID is NULL")
    @Min(value = ID_BOUND_MIN, message = "Invalid User ID: ID cannot be any lower than " + ID_BOUND_MIN)
    @Max(value = ID_BOUND_MAX, message = "Invalid User ID: ID cannot be any higher than " + ID_BOUND_MAX)
    @JsonProperty("id")
    Integer userID;

    @JsonProperty("balance")
    Optional<Double> balance = Optional.empty();

    @JsonProperty("roles")
    Optional<Set<UserRoles>> userRoles = Optional.empty();
}
