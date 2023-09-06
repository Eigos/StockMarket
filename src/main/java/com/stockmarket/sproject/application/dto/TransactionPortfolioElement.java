package com.stockmarket.sproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({"name", "symbol", "value", "unit_value", "quantity"})
@Builder
@Data
public class TransactionPortfolioElement {
    String name;

    String symbol;

    String value;

    @JsonProperty(value = "unit_value")
    String unitValue;

    String quantity;

}
