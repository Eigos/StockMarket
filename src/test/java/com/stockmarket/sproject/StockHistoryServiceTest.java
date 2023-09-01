package com.stockmarket.sproject;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.sproject.application.Service.StockHistoryService;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.webscrapper.BasicStockInformation;

@SpringBootTest
public class StockHistoryServiceTest {

    @InjectMocks
    StockHistoryService stockHistoryService;

    @Test
    public void Should_CalculateCurrentQuantity_When_StockHistoryExist() {

        String symbol = "SYMBOL";

        StockType stockType = StockType.builder()
            .id(0)
            .symbol(symbol)
            .build();

        stockHistoryService.calculateCurrentStockQuantity(stockType);
        
    }

}
