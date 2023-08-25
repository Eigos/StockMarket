package com.stockmarket.sproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockPurchaseRequest {
    
    @JsonProperty("stock_symbol")
    String stockSymbol;

    Integer quantity;

}
