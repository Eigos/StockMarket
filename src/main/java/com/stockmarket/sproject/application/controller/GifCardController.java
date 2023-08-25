package com.stockmarket.sproject.application.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.Service.GiftCardService;
import com.stockmarket.sproject.application.Service.StockAccessiblityService;
import com.stockmarket.sproject.application.Service.TransactionService;
import com.stockmarket.sproject.application.dto.GiftCardRequest;
import com.stockmarket.sproject.application.dto.GiftCardResponse;
import com.stockmarket.sproject.application.dto.GiftCardUseRequest;
import com.stockmarket.sproject.application.model.GiftCard;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@RestController
@RequestMapping("/gift-card")
public class GifCardController {

    private final GiftCardService giftCardService;

    GifCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    @GetMapping
    public ResponseEntity<GiftCardResponse> GenerateGiftCard(
            @Valid @RequestBody GiftCardRequest cardRequest) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

                

        return ResponseEntity.ok().body(
                giftCardService.generateGiftCard(
                        userDetails.getUsername(),
                        Optional.ofNullable(cardRequest.getTargetAccount()),
                        cardRequest.getValue()));
    }

    @PostMapping
    public ResponseEntity<String> UseGiftCard(
            @Valid @RequestBody GiftCardUseRequest giftCardUseRequest) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        giftCardService.UseGiftCard(username, giftCardUseRequest.getCardCode());

        return ResponseEntity.ok().build();
    }
}
