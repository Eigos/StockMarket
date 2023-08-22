package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.StockHistory;
import com.stockmarket.sproject.model.StockType;

import java.util.List;


public interface IStockHistoryRepository extends CrudRepository<StockHistory, Integer>{
    StockHistory findFirstByStockTypeOrderByUpdateTimeAsc(StockType stockType);
}
