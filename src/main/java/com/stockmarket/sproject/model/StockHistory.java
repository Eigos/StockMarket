package com.stockmarket.sproject.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StockHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    //@CreationTimestamp
    long updateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_type_id", referencedColumnName = "id")
    StockType stockType;

    double value;

    int quantity;

    @OneToOne(mappedBy = "stockHistory")
    TransactionHistory transactionHistory;
}
