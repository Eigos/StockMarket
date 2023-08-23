package com.stockmarket.sproject.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;


public interface IStockHistoryRepository extends CrudRepository<StockHistory, Integer>{
    StockHistory findFirstByStockTypeOrderByUpdateTimeAsc(StockType stockType);
}
