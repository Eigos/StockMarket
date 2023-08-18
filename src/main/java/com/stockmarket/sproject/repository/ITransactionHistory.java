package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.TransactionHistory;

public interface ITransactionHistory extends CrudRepository<TransactionHistory, Integer>{
    
}
