package com.stockmarket.sproject.application.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.StockQuantity;
import com.stockmarket.sproject.application.model.StockType;


public interface IStockQuantityRepository extends CrudRepository<StockQuantity, Integer> {
    Optional<StockQuantity> findByStockType(StockType stockType);
    Optional<StockQuantity> findByStockType_Symbol(String symbol);
}
