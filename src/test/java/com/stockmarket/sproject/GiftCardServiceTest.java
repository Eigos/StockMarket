package com.stockmarket.sproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.Service.GiftCardService;
import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.AccountDoesNotMatchException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.GiftCardInvalidException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.GiftCard;
import com.stockmarket.sproject.application.repository.IAccountRepository;
import com.stockmarket.sproject.application.repository.IGiftCardRepository;

@SpringBootTest
public class GiftCardServiceTest {

    @InjectMocks
    GiftCardService giftCardService;

    @Mock
    AccountService accountService;

    @Mock
    IGiftCardRepository giftCardRepository;

    @Mock
    IAccountRepository accountRepository;

    @Test
    public void Should_UpdateAccountBalance_When_GiftCardIsValid() {

        final int accountBalanceStart = 0;
        final int accountId = 1;
        final int giftCardValue = 1000;
        final String giftCardCode = "TESTCODE";
        final String accountMail = "TESTMAIL@gmail.com";

        Account accountTest = Account.builder()
                .id(accountId)
                .balance(accountBalanceStart)
                .email(accountMail)
                .build();

        Mockito.when(accountService.getAccountByEmail(accountMail)).thenReturn(accountTest);

        GiftCard giftCard = GiftCard.builder()
                .creator(accountTest)
                .targetAccount(null)
                .isValid(true)
                .value(giftCardValue)
                .cardCode(giftCardCode)
                .build();

        Mockito.when(giftCardRepository.findBycardCode(giftCardCode)).thenReturn(Optional.ofNullable(giftCard));

        giftCardService.UseGiftCard(accountMail, giftCardCode);

        assertEquals(accountService.getAccountByEmail(accountMail).getBalance(), accountBalanceStart + giftCardValue);

    }

    @Test
    public void Should_ThrowGiftCardInvalidException_When_GiftCardInvalid() {

        final int accountBalanceStart = 0;
        final int accountId = 1;
        final String giftCardCode = "TESTCODE";
        final String accountMail = "TESTMAIL@gmail.com";

        Account accountTest = Account.builder()
                .id(accountId)
                .balance(accountBalanceStart)
                .email(accountMail)
                .build();

        Mockito.when(accountService.getAccountByEmail(accountMail)).thenReturn(accountTest);

        GiftCard giftCard = GiftCard.builder()
                .creator(accountTest)
                .targetAccount(null)
                .isValid(false)
                .value(0)
                .cardCode(giftCardCode)
                .build();

        Mockito.when(giftCardRepository.findBycardCode(giftCardCode)).thenReturn(Optional.ofNullable(giftCard));

        assertThrows(GiftCardInvalidException.class, () -> giftCardService.UseGiftCard(accountMail, giftCardCode));
    }

    @Test
    public void Should_ThrowAccountDoesNotMatchException_When_TargetAccountDoesNotMatch() {

        final int accountId = 1;
        final String accountMail = "TESTMAIL@gmail.com";
        final int accountBalanceStart = 0;

        final int targetAccountId = 2;
        final String targetAccountMail = "TESTMAILTARGET@gmail.com";
        final int targetAccountBalanceStart = 0;

        final String giftCardCode = "TESTCODE";

        Account accountTest = Account.builder()
                .id(accountId)
                .balance(accountBalanceStart)
                .email(accountMail)
                .build();

        Account accountTestTarget = Account.builder()
                .id(targetAccountId)
                .balance(targetAccountBalanceStart)
                .email(targetAccountMail)
                .build();

        Mockito.when(accountService.getAccountByEmail(accountMail)).thenReturn(accountTest);
        Mockito.when(accountService.getAccountByEmail(targetAccountMail)).thenReturn(accountTestTarget);

        GiftCard giftCard = GiftCard.builder()
                .creator(accountTest)
                .targetAccount(accountTestTarget)
                .isValid(true)
                .value(0)
                .cardCode(giftCardCode)
                .build();

        Mockito.when(giftCardRepository.findBycardCode(giftCardCode)).thenReturn(Optional.ofNullable(giftCard));

        assertThrows(AccountDoesNotMatchException.class,
                () -> giftCardService.UseGiftCard(targetAccountMail, giftCardCode));
    }

    @Test
    public void Should_ReturnGiftCard_When_GivenCardCodeExists() {

        final String cardCode = "TESTCODE";

        GiftCard giftCard = GiftCard.builder()
                .creator(null)
                .targetAccount(null)
                .isValid(false)
                .value(0)
                .cardCode(cardCode)
                .build();

        Mockito.when(giftCardRepository.findBycardCode(cardCode)).thenReturn(Optional.ofNullable(giftCard));

        assertSame(giftCard, giftCardService.getCard(cardCode));
    }

    @Test
    public void Should_ThrowEntityNotFoundException_When_GivenCardCodeDoesNotExist() {

        final String cardCode = "TESTCODE";

        assertThrows(EntityNotFoundException.class, () -> giftCardService.getCard(cardCode));
    }

}
