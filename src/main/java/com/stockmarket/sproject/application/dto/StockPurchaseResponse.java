package com.stockmarket.sproject.application.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StockPurchaseResponse {

    @JsonProperty("stock_symbol")
    String stockSymbol;

    @JsonProperty("stock_name")
    String stockName;
    
    @JsonProperty("stock_unit_price")
    String stockUnitPrice;
    
    @JsonProperty("order_date")
    Date purchaseDate;
    
    @JsonProperty("requested_quantity")
    int requestedQuantity;
    
    @JsonProperty("total_price")
    String totalPrice; 

}
