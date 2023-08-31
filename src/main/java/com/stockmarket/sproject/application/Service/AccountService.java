package com.stockmarket.sproject.application.Service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.Security.UserRoles;
import com.stockmarket.sproject.application.dto.UserUpdateRequest;
import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.UserNotFoundException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void PermitAccount(Account account, Set<UserRoles> userRoles) {

        for (UserRoles userRole : userRoles)
            account.addRole(userRole);

        accountRepository.save(account);
    }

    public void PermitAccount(Integer accountId, Set<UserRoles> userRoles) {

        Account account = getAccountById(accountId);

        PermitAccount(account, userRoles);
    }

    public void UpdateBalance(Account account, Double newBalance) {

        account.setBalance(newBalance);

        accountRepository.save(account);
    }

    public void UpdateBalance(Integer accountId, Double newBalance) {
        Account account = getAccountById(accountId);

        UpdateBalance(account, newBalance);
    }

    public void UpdateAccount(UserUpdateRequest updateRequest) {

        Account account = getAccountById(updateRequest.getUserID());

        if (updateRequest.getUserRoles().isPresent())
            PermitAccount(account, updateRequest.getUserRoles().get());

        if (updateRequest.getBalance().isPresent())
            UpdateBalance(account, updateRequest.getBalance().get());

    }

    public Account getAccountById(int id) throws EntityNotFoundException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Account getAccountByEmail(String email) throws EntityNotFoundException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void save(Account account){
        accountRepository.save(account);
    }

}
