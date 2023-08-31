package com.stockmarket.sproject.application.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.stockmarket.sproject.application.model.GiftCard;


public interface IGiftCardRepository extends CrudRepository<GiftCard, Integer> {
    Optional<GiftCard> findBycardCode(String code);
}
