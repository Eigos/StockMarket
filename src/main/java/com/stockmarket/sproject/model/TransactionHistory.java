package com.stockmarket.sproject.model;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.stockmarket.sproject.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionHistory {
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    int id;

    @ManyToOne
    @JoinColumn(name = "account_portfolio_id")
    private Account account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_history_id", referencedColumnName = "id")
    private StockHistory stockHistory;

    private int quantity;
    
    @Enumerated(EnumType.STRING) 
    @Column(name="transaction_type")
    private TransactionType transactionType;

}
