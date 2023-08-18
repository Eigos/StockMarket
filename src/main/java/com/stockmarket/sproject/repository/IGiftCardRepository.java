package com.stockmarket.sproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.model.GiftCard;

public interface IGiftCardRepository extends CrudRepository<GiftCard, Integer> {
    
}
