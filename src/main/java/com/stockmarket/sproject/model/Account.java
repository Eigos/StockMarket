package com.stockmarket.sproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.stockmarket.sproject.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;
    
    String name;
    
    String lastName;

    @Email
    String email;

    String password;
    
    @OneToMany(mappedBy =  "account", fetch = FetchType.LAZY)
    @Column(name = "portfolio_id")
    private Set<TransactionHistory> transactionHistory = new HashSet<>();
    
    @ElementCollection(targetClass=Roles.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name = "account_roles")
    @Column(name="roles")
    private Set<Roles> roles = new HashSet<>();

    double balance;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    Set<GiftCard> creatorGiftCards = new HashSet<>();

    @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
    Set<GiftCard> targetGiftCards = new HashSet<>();

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    RuleSet ruleSet;
    
}
