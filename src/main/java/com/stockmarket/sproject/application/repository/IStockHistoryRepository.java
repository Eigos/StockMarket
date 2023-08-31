package com.stockmarket.sproject.application.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;


public interface IStockHistoryRepository extends CrudRepository<StockHistory, Integer>{
    Optional<StockHistory> findFirstByStockTypeOrderByUpdateTimeAsc(StockType stockType);
}
