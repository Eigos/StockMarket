package com.stockmarket.sproject.application.Service;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.exception_handler.custom_exceptions.StockTypeDoesNotExistException;
import com.stockmarket.sproject.application.model.StockQuantity;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.repository.IStockQuantityRepository;

@Service
public class StockQuantityService {
    
    private final IStockQuantityRepository stockQuantityRepository;
    
    public static final int DEFUALT_QUANTITY = 1000;

    StockQuantityService(IStockQuantityRepository stockQuantityRepository){
        this.stockQuantityRepository = stockQuantityRepository;
    }

    public void UpdateStockQuantity(StockType stockType, int amount){
        StockQuantity stockQuantity = null;

        try {
            stockQuantity = getStockQuantity(stockType);
        } catch (StockTypeDoesNotExistException e) {
            stockQuantity = StockQuantity.builder().stockType(stockType).build();
        }

        stockQuantity.setQuantity(amount);

        stockQuantityRepository.save(stockQuantity);
    }

    public void UpdateStockQuantity(StockType stockType){
        UpdateStockQuantity(stockType, DEFUALT_QUANTITY);
    }

    public StockQuantity getStockQuantity(String symbol) throws StockTypeDoesNotExistException{
        return stockQuantityRepository.findByStockType_Symbol(symbol).orElseThrow(() -> new StockTypeDoesNotExistException("Stock type does not exist"));
    }

    public StockQuantity getStockQuantity(StockType stockType) throws StockTypeDoesNotExistException{
        return stockQuantityRepository.findByStockType(stockType).orElseThrow(() -> new StockTypeDoesNotExistException("Stock type does not exist"));
    }

}
