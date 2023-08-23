package com.stockmarket.sproject.application.repository;

import org.springframework.data.repository.CrudRepository;
import com.stockmarket.sproject.application.model.Account;

public interface IAccountRepository extends CrudRepository<Account, Integer>{
    Account findByEmail(String email);
}
