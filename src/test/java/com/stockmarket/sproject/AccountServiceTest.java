package com.stockmarket.sproject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.stockmarket.sproject.Security.UserRoles;
import com.stockmarket.sproject.Security.Jwt.Authoritie;
import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@SpringBootTest
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    IAccountRepository accountRepository;

    @DisplayName(value = "This test should pass when account balance replaced by given amount")
    @CsvSource(value = { "0,2000", "2000,5000", "0,-10", "0,0", "2000,0" })
    @ParameterizedTest
    public void Should_ReplaceAccountBalance_When_GivenAmountIsValid(double bStart, double bAmount) {

        final int userId = 1;
        final double balanceAmount = bAmount;
        final double balanceStart = bStart;

        Optional<Account> accountTest = Optional
                .ofNullable(Account.builder().id(userId).name("TestName").balance(balanceStart).build());

        Mockito.when(accountRepository.findById(accountTest.get().getId())).thenReturn(accountTest);

        accountService.UpdateBalance(userId, balanceAmount);

        Account account = accountRepository.findById(userId).get();

        assertEquals(account.getBalance(), balanceAmount);
    }

    @Test
    public void Should_FindAccount_When_AccountAssociatedWithGivenIDExists() {

        final int userId = 1;

        Optional<Account> accountTest = Optional.ofNullable(Account.builder().id(userId).build());

        Mockito.when(accountRepository.findById(accountTest.get().getId())).thenReturn(accountTest);

        Account account = accountService.getAccountById(userId);

        assertEquals(accountTest.get(), account);
    }

    @Test
    public void Should_ThrowEntityNotFoundException_When_EntityDoesNotExist() {

        final int userId = 1;

        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountById(userId));
    }

    @Test
    public void Should_PreventUnnecessaryDuplicationOfRoles_When_GivenRoleAlreadyExists() {

        final int userId = 1;

        UserRoles givenRole = UserRoles.ROLE_USER;

        Set<UserRoles> userRoles = new HashSet<>();
        userRoles.add(givenRole);

        Optional<Account> accountTest = Optional.ofNullable(Account.builder()
                .id(userId)
                .roles(Stream.of(new UserRoles[] {}).collect(Collectors.toSet()))
                .build());

        Mockito.when(accountRepository.findById(accountTest.get().getId())).thenReturn(accountTest);

        accountService.PermitAccount(accountRepository.findById(userId).get(), userRoles);
        accountService.PermitAccount(accountRepository.findById(userId).get(), userRoles);

        assertAll(() -> {

            Set<Authoritie> accountAuthorities = (Set<Authoritie>) accountRepository.findById(userId).get().getAuthorities();

            Set<Authoritie> authorities = userRoles
                    .stream()
                    .map(Authoritie::new)
                    .collect(Collectors.toSet());

            assertTrue(() -> {
                return authorities.size() == accountAuthorities.size();
            });
            assertTrue(() -> {
                if (!authorities.containsAll(accountAuthorities))
                    return false;

                if (!accountAuthorities.containsAll(authorities))
                    return false;

                return true;
            });

        });
    }

}
