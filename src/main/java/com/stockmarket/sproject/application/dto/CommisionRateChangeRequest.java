package com.stockmarket.sproject.application.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommisionRateChangeRequest {
    
    @NotNull(message = "Invalid Commision Rate: Commision Rate is NULL")
    @JsonProperty("commission_rate")
    double commissionRate;

}
