package com.stockmarket.sproject.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.TransactionHistory;

public interface ITransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer>{
    List<TransactionHistory> findAllByStockHistory(StockHistory stockHistory);
    List<TransactionHistory> findAllByAccount(Account account);
}
