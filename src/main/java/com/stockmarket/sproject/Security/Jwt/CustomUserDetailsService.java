package com.stockmarket.sproject.Security.Jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private IAccountRepository accountRepository;

    public CustomUserDetailsService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return accountRepository.findByEmail(username).orElse(null);
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
