package com.stockmarket.sproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String name;

    String symbol;

    String description;

    @OneToOne(mappedBy = "stockType")
    StockHistory stockHistory;

    @OneToOne(mappedBy = "stockType")
    RuleSet ruleSet;
}
