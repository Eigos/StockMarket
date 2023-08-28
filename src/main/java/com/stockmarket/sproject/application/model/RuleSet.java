package com.stockmarket.sproject.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.stockmarket.sproject.application.enums.RuleType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RuleSet {
    
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_type_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    StockType stockType;

    @Enumerated(EnumType.STRING) 
    @Column(name="rule_type")
    @EqualsAndHashCode.Exclude
    RuleType ruleType;

    double value;
}
