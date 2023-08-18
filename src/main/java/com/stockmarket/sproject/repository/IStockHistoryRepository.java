package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.StockHistory;

public interface IStockHistoryRepository extends CrudRepository<StockHistory, Integer>{
    
}
