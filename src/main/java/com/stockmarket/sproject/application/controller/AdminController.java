package com.stockmarket.sproject.application.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.Service.GiftCardService;
import com.stockmarket.sproject.application.Service.StockAccessiblityService;
import com.stockmarket.sproject.application.Service.TransactionService;
import com.stockmarket.sproject.application.dto.CommisionRateChangeRequest;
import com.stockmarket.sproject.application.dto.GiftCardRequest;
import com.stockmarket.sproject.application.dto.GiftCardResponse;
import com.stockmarket.sproject.application.dto.UserUpdateRequest;
import com.stockmarket.sproject.application.model.GiftCard;
import com.stockmarket.sproject.application.repository.IAccountRepository;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final AuthRestController authRestController;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final StockAccessiblityService stockAccessiblityService;

    AdminController(
            AuthRestController authRestController,
            AccountService accountService,
            TransactionService transactionService,
            StockAccessiblityService stockAccessiblityService,
            GiftCardService giftCardService,
            IAccountRepository accountRepository) {
        this.authRestController = authRestController;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.stockAccessiblityService = stockAccessiblityService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> CreateUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        return authRestController.signUp(signUpRequest);
    }

    @PatchMapping("/user")
    public ResponseEntity<String> UpdateUser(@Valid @RequestBody UserUpdateRequest updateRequest) {

        accountService.UpdateAccount(updateRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/commission")
    public ResponseEntity<String> ChangeCommissionRate(
            @Valid @RequestBody CommisionRateChangeRequest commisionRateChangeRequest) {

        transactionService.ChangeCommissionRate(commisionRateChangeRequest.getCommissionRate());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/transaction-pause")
    public ResponseEntity<String> PauseTransaction() {

        stockAccessiblityService.PauseAll();

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/transaction-pause/{stock_id}")
    public ResponseEntity<String> PauseTransaction(@PathVariable(value = "stock_id", required = true) Integer stockId) {

        stockAccessiblityService.Pause(stockId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/transaction-resume")
    public ResponseEntity<String> ResumeTransaction() {

        stockAccessiblityService.ResumeAll();

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/transaction-resume/{stock_id}")
    public ResponseEntity<String> ResumeTransaction(
            @PathVariable(value = "stock_id", required = true) Integer stockId) {

        stockAccessiblityService.Resume(stockId);

        return ResponseEntity.ok().build();
    }

    

}
