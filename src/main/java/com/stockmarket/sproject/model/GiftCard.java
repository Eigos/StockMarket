package com.stockmarket.sproject.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GiftCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    Account creator;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    Account targetAccount;

    double value;

    boolean isValid;

    //@CreationTimestamp
    Timestamp creationTime;

    long usedTime;

    long expireTime;

}
