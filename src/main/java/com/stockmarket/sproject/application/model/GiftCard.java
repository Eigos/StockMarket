package com.stockmarket.sproject.application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
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
@Builder
@EqualsAndHashCode
public class GiftCard {

    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    @EqualsAndHashCode.Exclude
    Account creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    @EqualsAndHashCode.Exclude
    Account targetAccount;

    double value;

    boolean isValid;

    @CreationTimestamp
    Date creationTime;

    Date usedTime;

    Date expireTime;

    String cardCode;

    @javax.persistence.PostPersist
    private void setExpireTime() {
        if (creationTime != null) {
            long expireDelay = 60 * 24 * 60 * 60 * 1000; // day * hour * min * sec * ms
            expireTime = new Date(creationTime.getTime() + expireDelay);
        }else{
            System.out.println("creation time is null");
        }
    }

}
