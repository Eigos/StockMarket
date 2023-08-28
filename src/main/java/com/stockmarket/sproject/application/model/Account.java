package com.stockmarket.sproject.application.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stockmarket.sproject.Security.UserRoles;
import com.stockmarket.sproject.Security.Jwt.Authoritie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode
public class Account implements UserDetails {

    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(name = "sequenceIdGenerator", strategy = "sequence", parameters = @Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"))
    @Column(updatable = false, nullable = false)
    int id;

    String name;

    String lastName;

    String email;

    String password;

    @Builder.Default
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Column(name = "portfolio_id")
    @EqualsAndHashCode.Exclude
    private Set<TransactionHistory> transactionHistory = new HashSet<>();

    @Builder.Default
    @ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "account_roles")
    @Column(name = "roles")
    @EqualsAndHashCode.Exclude
    private Set<UserRoles> roles = new HashSet<>();

    double balance;

    @Builder.Default
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    Set<GiftCard> creatorGiftCards = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetAccount", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    Set<GiftCard> targetGiftCards = new HashSet<>();

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    RuleSet ruleSet;

    @PostPersist
    public void PostPersist() {
        roles.add(UserRoles.ROLE_USER);
        roles.add(UserRoles.ROLE_ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authoritie> authorities = getRoles()
                .stream()
                .map(Authoritie::new)
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(UserRoles role){
        if(!roles.contains(role))
            roles.add(role);
    }

}
