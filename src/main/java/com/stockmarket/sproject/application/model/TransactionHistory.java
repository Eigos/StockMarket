package com.stockmarket.sproject.application.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.stockmarket.sproject.application.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@EqualsAndHashCode
public class TransactionHistory {
    
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;

    @ManyToOne
    @JoinColumn(name = "account_portfolio_id")
    @EqualsAndHashCode.Exclude
    private Account account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_history_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private StockHistory stockHistory;

    private int quantity;
    
    @Enumerated(EnumType.STRING) 
    @Column(name="transaction_type")
    @EqualsAndHashCode.Exclude
    private TransactionType transactionType;

    @CreationTimestamp
    private Date transactionDate;

    private double commissionRate;

}
