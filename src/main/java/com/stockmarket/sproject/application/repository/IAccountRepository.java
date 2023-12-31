package com.stockmarket.sproject.application.repository;

import org.springframework.data.repository.CrudRepository;
import com.stockmarket.sproject.application.model.Account;
import java.util.Optional;


public interface IAccountRepository extends CrudRepository<Account, Integer>{
    Optional<Account> findByEmail(String email);
}
