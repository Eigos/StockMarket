package com.stockmarket.sproject.application.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.RuleSet;


public interface IRuleSetRepository extends CrudRepository<RuleSet, Integer>{
    
}
