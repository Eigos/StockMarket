package com.stockmarket.sproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.StockHistory;
import com.stockmarket.sproject.model.TransactionHistory;

public interface ITransactionHistory extends CrudRepository<TransactionHistory, Integer>{
    List<TransactionHistory> findAllByStockHistory(StockHistory stockHistory);
}
