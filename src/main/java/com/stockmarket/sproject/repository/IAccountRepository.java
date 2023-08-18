package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.Account;

public interface IAccountRepository extends CrudRepository<Account, Integer>{
    
}
