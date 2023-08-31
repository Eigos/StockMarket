package com.stockmarket.sproject.application.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.StockType;



public interface IStockTypeRepository extends CrudRepository<StockType, Integer>{
    Optional<StockType> findFirstBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
