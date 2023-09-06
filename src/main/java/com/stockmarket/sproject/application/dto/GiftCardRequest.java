package com.stockmarket.sproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GiftCardRequest {

    @JsonProperty(value = "target_account")
    String targetAccount;

    Double value;

    @JsonProperty(value = "valid")
    Boolean isValid;

}
