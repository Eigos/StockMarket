package com.stockmarket.sproject.application.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode
@Builder
public class StockType {

    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;

    String name;

    String symbol;

    String description;

    @OneToMany(mappedBy = "stockType", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    Set<StockHistory> stockHistory;

    @OneToOne(mappedBy = "stockType", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    RuleSet ruleSet;

    @OneToMany(mappedBy = "stockType", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    Set<StockHistory> stockQuantity;
}
