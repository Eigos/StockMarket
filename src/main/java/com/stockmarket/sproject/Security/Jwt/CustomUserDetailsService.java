package com.stockmarket.sproject.Security.Jwt;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.Security.UserRoles;
import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username);
    }

    public void createAccount(SignUpRequest accountRequest){
        
        accountRepository.save(
            Account.builder()
            .email(accountRequest.getEmail())
            .name(accountRequest.getName())
            .lastName(accountRequest.getLastName())
            .password(accountRequest.getPassword())
            .build()
        );
    }
}
