package com.stockmarket.sproject.application.Service;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.repository.IStockTypeRepository;

@Service
public class StockTypeService {

    private final IStockTypeRepository stockTypeRepository;

    StockTypeService(IStockTypeRepository stockTypeRepository){
        this.stockTypeRepository = stockTypeRepository;
    }

    int getStockTypeId(String symbol) throws EntityNotFoundException {
        return getStockType(symbol).getId();
    }

    StockType getStockType(String symbol) throws EntityNotFoundException {
        return stockTypeRepository
                .findFirstBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(StockType.class, "symbol", symbol));
    }

    Iterable<StockType> getAll() {
        return stockTypeRepository.findAll();
    }

}
