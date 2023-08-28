package com.stockmarket.sproject.application.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode
public class StockHistory {
    
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;

    @CreationTimestamp
    Date updateTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    StockType stockType;

    double value;

    int quantity;

    @OneToOne(mappedBy = "stockHistory", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    TransactionHistory transactionHistory;
}
