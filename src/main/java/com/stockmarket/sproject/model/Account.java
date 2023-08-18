package com.stockmarket.sproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import com.stockmarket.sproject.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    
    String name;
    
    String lastName;

    @Email
    String email;

    String password;
    
    @OneToMany(mappedBy =  "account")
    @Column(name = "portfolio_id")
    private Set<TransactionHistory> transactionHistory = new HashSet<>();
    
    @ElementCollection(targetClass=Roles.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name = "account_roles")
    @Column(name="roles")
    private Set<Roles> roles = new HashSet<>();

    double balance;

    @OneToMany(mappedBy = "creator")
    Set<GiftCard> creatorGiftCards = new HashSet<>();

    @OneToMany(mappedBy = "targetAccount")
    Set<GiftCard> targetGiftCards = new HashSet<>();

    @OneToOne(mappedBy = "account")
    RuleSet ruleSet;
    
}
