package com.stockmarket.sproject.webscrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasicStockInformation {
    
    private String symbol;
    private String description;
    private double value;

}
