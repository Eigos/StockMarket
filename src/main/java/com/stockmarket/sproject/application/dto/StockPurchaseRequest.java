package com.stockmarket.sproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockPurchaseRequest {
    
    @JsonProperty("stock_symbol")
    String stockSymbol;

    Integer quantity;

}
