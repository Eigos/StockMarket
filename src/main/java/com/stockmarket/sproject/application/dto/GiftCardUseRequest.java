package com.stockmarket.sproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GiftCardUseRequest {
    
    @JsonProperty(value = "card_code")
    String cardCode;
}
