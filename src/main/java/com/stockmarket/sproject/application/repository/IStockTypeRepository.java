package com.stockmarket.sproject.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.StockType;



public interface IStockTypeRepository extends CrudRepository<StockType, Integer>{
    StockType findFirstBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
