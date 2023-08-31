package com.stockmarket.sproject.application.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.dto.StockPurchaseRequest;
import com.stockmarket.sproject.application.dto.StockPurchaseResponse;
import com.stockmarket.sproject.application.dto.StockSellRequest;
import com.stockmarket.sproject.application.dto.TransactionHistoryElement;
import com.stockmarket.sproject.application.dto.TransactionHistoryResponse;
import com.stockmarket.sproject.application.enums.TransactionType;
import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.MessageException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.NotEnoughBalanceException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.NotEnoughStockException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.StockAccessibilityException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.TransactionException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.model.TransactionHistory;
import com.stockmarket.sproject.application.repository.ITransactionHistoryRepository;
import com.stockmarket.sproject.application.util.UtilMethods;

@Service
public class TransactionService {

    public static final double DEFAUL_COMMISSION_RATE = 3.0;

    public static double commissionRate = DEFAUL_COMMISSION_RATE;

    private double systemBalance = 0.0;

    private final ITransactionHistoryRepository transactionHistoryRepository;

    private final AccountService accountService;

    private final StockTypeService stockTypeService;

    private final StockHistoryService stockHistoryService;

    private final StockAccessiblityService stockAccessiblityService;
    
    TransactionService(
            AccountService accountService,
            StockTypeService stockTypeService,
            @Lazy StockHistoryService stockHistoryService,
            ITransactionHistoryRepository transactionHistoryRepository,
            StockAccessiblityService stockAccessiblityService) {
        this.accountService = accountService;
        this.stockTypeService = stockTypeService;
        this.stockHistoryService = stockHistoryService;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.stockAccessiblityService = stockAccessiblityService;
    }

    public void ChangeCommissionRate(double newRate) {
        commissionRate = newRate;
    }

    public StockPurchaseResponse PurchaseStock(String username, StockPurchaseRequest stockPurchaseRequest)
            throws TransactionException {
        Account account = accountService.getAccountByEmail(username);
        StockType stockType = stockTypeService.getStockType(stockPurchaseRequest.getStockSymbol());
        StockHistory stockHistoryElement = stockHistoryService.getRecentHistory(stockType);
        int quantity = stockHistoryElement.getQuantity();
        int desiredQuantity = stockPurchaseRequest.getQuantity();
        double unitPrice = stockHistoryElement.getValue();

        if (!stockAccessiblityService.isStockAccessible(stockType.getId()))
            throw new StockAccessibilityException("Stock is closed to transaction");

        // Not enough stock
        if (StockQuantityCheck(desiredQuantity, quantity))
            throw new NotEnoughStockException("Not enough stock");

        if (!CanPurchaseStock(account.getBalance(), unitPrice, desiredQuantity))
            throw new NotEnoughBalanceException("Not enough balance");

        account.setBalance(account.getBalance() - PurchasePriceCalculateTotal(unitPrice, desiredQuantity));

        systemBalance += PurchaseCalculateCommission(unitPrice, desiredQuantity);

        transactionHistoryRepository.save(
                TransactionHistory.builder()
                        .account(account)
                        .stockHistory(stockHistoryElement)
                        .quantity(desiredQuantity)
                        .transactionType(TransactionType.PURCHASE)
                        .commissionRate(getCommissionRate())
                        .build());

        accountService.save(account);

        return StockPurchaseResponse.builder()
                .stockSymbol(stockType.getSymbol())
                .stockName(stockType.getName())
                .stockUnitPrice(UtilMethods.FormatDouble(unitPrice))
                .purchaseDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .requestedQuantity(desiredQuantity)
                .totalPrice(UtilMethods.FormatDouble(PurchasePriceCalculateTotal(unitPrice, desiredQuantity)))
                .build();
    }

    private int getCurrentQuantity(Account account, String symbol) {
        int quantity = 0;
        
        for (TransactionHistory transactionHistory : account.getTransactionHistory()) {
            if (transactionHistory.getStockHistory().getStockType().getSymbol() != symbol)
                continue;

            quantity += (transactionHistory.getTransactionType() == TransactionType.PURCHASE)
                    ? transactionHistory.getQuantity()
                    : transactionHistory.getQuantity() * (-1);
        }

        return quantity;
    }

    public StockPurchaseResponse SellStock(String username, StockSellRequest stockSellRequest) 
        throws NotEnoughStockException, StockAccessibilityException, EntityNotFoundException, RuntimeException{

        Account account = accountService.getAccountByEmail(username);
        StockType stockType = stockTypeService.getStockType(stockSellRequest.getStockSymbol());
        StockHistory stockHistoryElement = stockHistoryService.getRecentHistory(stockType);

        int quantity = getCurrentQuantity(account, stockType.getSymbol());

        int desiredQuantity = stockSellRequest.getQuantity();
        double unitPrice = stockHistoryElement.getValue();

        if (!stockAccessiblityService.isStockAccessible(stockType.getId()))
            throw new StockAccessibilityException("Stock is closed to transaction");

        // Not enough stock
        if (StockQuantityCheck(desiredQuantity, quantity))
            throw new NotEnoughStockException("Not enough stock");

        account.setBalance(account.getBalance() + (PurchasePriceCalculate(unitPrice, desiredQuantity)
                - PurchaseCalculateCommission(unitPrice, desiredQuantity)));

        systemBalance += PurchaseCalculateCommission(unitPrice, desiredQuantity);

        transactionHistoryRepository.save(
                TransactionHistory.builder()
                        .account(account)
                        .stockHistory(stockHistoryElement)
                        .quantity(desiredQuantity)
                        .transactionType(TransactionType.SELL)
                        .commissionRate(getCommissionRate())
                        .build());

        accountService.save(account);

        return StockPurchaseResponse.builder()
                .stockSymbol(stockType.getSymbol())
                .stockName(stockType.getName())
                .stockUnitPrice(UtilMethods.FormatDouble(unitPrice))
                .purchaseDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .requestedQuantity(desiredQuantity)
                .totalPrice(UtilMethods.FormatDouble((PurchasePriceCalculate(unitPrice, desiredQuantity)
                        - PurchaseCalculateCommission(unitPrice, desiredQuantity))))
                .build();

    }

    private boolean CanPurchaseStock(double balance, double stockUnitPrice, int quantity) {
        return (balance - PurchasePriceCalculateTotal(stockUnitPrice, quantity)) >= 0;
    }

    private double PurchasePriceCalculate(double stockUnitPrice, int quantity) {
        return (stockUnitPrice * quantity);
    }

    private double PurchaseCalculateCommission(double unitPrice, int quantity) {
        return ((unitPrice * quantity) * (commissionRate)) / 100.0;
    }

    private double PurchasePriceCalculateTotal(double unitPrice, int quantity) {
        return PurchasePriceCalculate(unitPrice, quantity) + PurchaseCalculateCommission(unitPrice, quantity);
    }

    private boolean StockQuantityCheck(int requestedQuantity, int stockQuantity) {
        return requestedQuantity > stockQuantity;
    }

    public TransactionHistoryResponse StockHistory(String username) throws Exception {

        Account account = accountService.getAccountByEmail(username);

        List<TransactionHistoryElement> elements = new ArrayList<TransactionHistoryElement>();

        List<TransactionHistory> transactionHistory = transactionHistoryRepository.findAllByAccount(account);

        for (TransactionHistory transactionHistoryElement : transactionHistory) {

            StockHistory stockHistory = transactionHistoryElement.getStockHistory();
            StockType stockType = stockHistory.getStockType();

            elements.add(
                    TransactionHistoryElement.builder()
                            .symbol(stockType.getSymbol())
                            .name(stockType.getName())
                            .requestedAmount(UtilMethods.FormatDouble(transactionHistoryElement.getQuantity()))
                            .value(UtilMethods.FormatDouble(stockHistory.getValue()))
                            .commissionCut(UtilMethods.FormatDouble(PurchaseCalculateCommission(stockHistory.getValue(),
                                    transactionHistoryElement.getQuantity())))
                            .totalPrice(UtilMethods.FormatDouble(PurchasePriceCalculateTotal(stockHistory.getValue(),
                                    transactionHistoryElement.getQuantity())))
                            .date(transactionHistoryElement.getTransactionDate())
                            .transactionType(transactionHistoryElement.getTransactionType().name())
                            .build());

        }

        TransactionHistoryResponse transactionHistoryResponse = TransactionHistoryResponse.builder()
                .balance(UtilMethods.FormatDouble(account.getBalance()))
                .name(account.getName())
                .lastName(account.getLastName())
                .elements(elements)
                .build();

        return transactionHistoryResponse;
    }

    public List<TransactionHistory> getAll(StockHistory stockHistory){
        return transactionHistoryRepository.findAllByStockHistory(stockHistory);
    }

    public double getCommissionRate() {
        return commissionRate;
    }

}
