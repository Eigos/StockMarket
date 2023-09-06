package com.stockmarket.sproject.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({"name", "last_name", "balance", "total_portfolio_value", "elements"})
@Builder
@Data
public class TransactionPortfolio {

    String name;

    @JsonProperty(value = "last_name")
    String lastName;

    String balance;

    @JsonProperty(value = "total_portfolio_value")
    String valueTotal;

    List<TransactionPortfolioElement> elements;
}
