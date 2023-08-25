package com.stockmarket.sproject.application.dto;

import java.util.Optional;

import org.springframework.lang.Nullable;

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
