package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.RuleSet;

public interface IRuleSetRepository extends CrudRepository<RuleSet, Integer>{
    
}
