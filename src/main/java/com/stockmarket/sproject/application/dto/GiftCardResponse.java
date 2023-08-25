package com.stockmarket.sproject.application.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCardResponse {
    
    @JsonProperty(value = "is_valid")
    boolean isValid;

    double value;

    @JsonProperty(value = "time_create")
    Date createTime;

    @JsonProperty(value = "time_expire")
    Date expireTime;

    @JsonProperty(value = "card_code")
    String cardCode;

}
