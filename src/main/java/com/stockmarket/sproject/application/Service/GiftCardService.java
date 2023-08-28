package com.stockmarket.sproject.application.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.UtilMethods;
import com.stockmarket.sproject.application.dto.GiftCardResponse;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.GiftCard;
import com.stockmarket.sproject.application.repository.IAccountRepository;
import com.stockmarket.sproject.application.repository.IGiftCardRepository;

@Service
public class GiftCardService {

    IGiftCardRepository giftCardRepository;
    IAccountRepository accountRepository;

    GiftCardService(IGiftCardRepository giftCardRepository,
            IAccountRepository accountRepository) {
        this.giftCardRepository = giftCardRepository;
        this.accountRepository = accountRepository;
    }

    public GiftCardResponse generateGiftCard(String creatorAccountStr, Optional<String> targeAccountStr, double value) {

        Account creatorAccount = accountRepository.findByEmail(creatorAccountStr);
        
        Account targeAccount = targeAccountStr.isPresent() ? accountRepository.findByEmail(targeAccountStr.get()) : null;

        String giftCardCode = UtilMethods.KeyGenerator.generateKey();

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

    public void UseGiftCard(String accountStr, String cardCode) throws Exception{

        Account account = accountRepository.findByEmail(accountStr);

        if(account == null)
            throw new Exception("Account could not found");

        GiftCard giftCard = giftCardRepository.findBycardCode(cardCode);

        if(giftCard == null)
            throw new Exception("Unable to find gift card");

        if(giftCard.getTargetAccount() != null){

            if(Integer.compare(giftCard.getTargetAccount().getId(), account.getId()) != 1)
                throw new Exception("Given account and gift card's target account does not match");
        }

        if(!giftCard.getCardCode().equals(cardCode))
            throw new Exception("Gift card code does not match");

        account.setBalance(account.getBalance() + giftCard.getValue());

        accountRepository.save(account);

        giftCard.setUsedTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        giftCard.setValid(false);
    }

}
