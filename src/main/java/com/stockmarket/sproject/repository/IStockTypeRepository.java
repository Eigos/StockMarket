package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.StockType;


public interface IStockTypeRepository extends CrudRepository<StockType, Integer>{
    StockType findFirstBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
