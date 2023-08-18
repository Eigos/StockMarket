package com.stockmarket.sproject.model;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.stockmarket.sproject.enums.RuleType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RuleSet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_type_id", referencedColumnName = "id")
    StockType stockType;

    @Enumerated(EnumType.STRING) 
    @Column(name="rule_type")
    RuleType ruleType;

    double value;
}
