package com.stockmarket.sproject.application.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@JsonPropertyOrder({"name", "symbol", "requested_amount", "value", "commission_cut", "total_price", "date"})
public class TransactionHistoryElement {
    
    String name;

    String symbol;

    @JsonProperty(value = "requested_amount")
    String requestedAmount;

    String value;

    @JsonProperty(value = "commission_cut")
    String commissionCut;

    @JsonProperty(value = "total_price")
    String totalPrice;

    @JsonProperty(value = "purchase_date")
    Date date;
}
