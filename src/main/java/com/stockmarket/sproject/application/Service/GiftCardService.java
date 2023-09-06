package com.stockmarket.sproject.application.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.dto.GiftCardResponse;
import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.AccountDoesNotMatchException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.GiftCardInvalidException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.GiftCard;
import com.stockmarket.sproject.application.repository.IGiftCardRepository;
import com.stockmarket.sproject.application.util.BasicKeyGenerator;
import com.stockmarket.sproject.application.util.IKeyGenerator;

@Service
public class GiftCardService {

    private final IGiftCardRepository giftCardRepository;

    private final AccountService accountService;

    GiftCardService(IGiftCardRepository giftCardRepository,
            AccountService accountService) {
        this.giftCardRepository = giftCardRepository;
        this.accountService = accountService;
    }

    public GiftCardResponse generateGiftCard(String creatorAccountStr, Optional<String> targeAccountStr, double value) {

        Account creatorAccount = accountService.getAccountByEmail(creatorAccountStr);

        Account targeAccount = targeAccountStr.isPresent() ? accountService.getAccountByEmail(targeAccountStr.get())
                : null;

        IKeyGenerator keyGenerator = new BasicKeyGenerator();

        String giftCardCode = keyGenerator.getKey();

        GiftCard giftCard = GiftCard.builder()
                .creator(creatorAccount)
                .targetAccount(targeAccount)
                .isValid(true)
                .value(value)
                .cardCode(giftCardCode)
                .build();

        giftCardRepository.save(giftCard);

        return GiftCardResponse.builder()
                .isValid(giftCard.isValid())
                .createTime(giftCard.getCreationTime())
                .expireTime(giftCard.getExpireTime())
                .value(giftCard.getValue())
                .cardCode(giftCard.getCardCode())
                .build();
    }

    public void UseGiftCard(String accountStr, String cardCode) throws RuntimeException {

        Account account = accountService.getAccountByEmail(accountStr);

        GiftCard giftCard = getCard(cardCode);

        if (giftCard.isValid() == false)
            throw new GiftCardInvalidException("Invalid gift card. Gift card may have already been used.");

        if (giftCard.getTargetAccount() != null) {

            if (Integer.compare(giftCard.getTargetAccount().getId(), account.getId()) != 1)
                throw new AccountDoesNotMatchException("Given account and gift card's target account does not match");
        }

        account.setBalance(account.getBalance() + giftCard.getValue());

        accountService.save(account);

        //giftCard.setUsedTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        giftCard.setUsedTime(new Date(System.currentTimeMillis()));

        giftCard.setValid(false);

        giftCardRepository.save(giftCard);
    }

    public GiftCard getCard(String cardCode) {
        return giftCardRepository.findBycardCode(cardCode)
                .orElseThrow(() -> new EntityNotFoundException(GiftCard.class, "cardCode", cardCode));
    }

}
