package com.stockmarket.sproject.application.controller;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.application.Service.TransactionService;
import com.stockmarket.sproject.application.dto.StockPurchaseRequest;
import com.stockmarket.sproject.application.dto.StockPurchaseResponse;
import com.stockmarket.sproject.application.dto.StockSellRequest;
import com.stockmarket.sproject.application.dto.TransactionHistoryResponse;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @PostMapping("/purchase")
    public ResponseEntity<StockPurchaseResponse> PurchaseStock(
            @Valid @RequestBody StockPurchaseRequest stockPurchaseRequest) throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        StockPurchaseResponse responseBody = transactionService.PurchaseStock(username, stockPurchaseRequest);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/sell")
    public ResponseEntity<StockPurchaseResponse> SellStock(
        @Valid @RequestBody StockSellRequest stockSellRequest) throws Exception{
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        StockPurchaseResponse responseBody = transactionService.SellStock(username, stockSellRequest);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/history")
    public ResponseEntity<TransactionHistoryResponse> getPurchaseStockHistory(){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        TransactionHistoryResponse responseBody = transactionService.StockHistory(username);

        return ResponseEntity.ok(responseBody);
    }

}
