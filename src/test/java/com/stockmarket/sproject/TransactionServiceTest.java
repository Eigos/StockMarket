package com.stockmarket.sproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.Service.StockAccessiblityService;
import com.stockmarket.sproject.application.Service.StockHistoryService;
import com.stockmarket.sproject.application.Service.StockQuantityService;
import com.stockmarket.sproject.application.Service.StockTypeService;
import com.stockmarket.sproject.application.Service.TransactionService;
import com.stockmarket.sproject.application.dto.StockPurchaseRequest;
import com.stockmarket.sproject.application.dto.StockSellRequest;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.NotEnoughBalanceException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.NotEnoughStockException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.StockAccessibilityException;
import com.stockmarket.sproject.application.model.Account;
import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockQuantity;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.repository.ITransactionHistoryRepository;

@SpringBootTest
public class TransactionServiceTest {

    @Spy
    @InjectMocks
    TransactionService transactionService;

    @Mock
    ITransactionHistoryRepository transactionHistoryRepository;

    @Mock
    AccountService accountService;

    @Mock
    StockTypeService stockTypeService;

    @Mock
    StockHistoryService stockHistoryService;

    @Mock
    StockAccessiblityService stockAccessiblityService;

    @Mock
    StockQuantityService stockQuantityService;

    final String username = "TEST";
    final String stockTypeSombol = "Symbol";

    private Account account;
    private StockPurchaseRequest stockPurchaseRequest;
    private StockType stockType;
    private StockHistory stockHistory;
    private StockQuantity stockQuantity;

    private static final double ACCOUNT_BALANCE = 100;
    private static final int STOCK_QUANTITY = 1;
    private static final double STOCK_VALUE = 5;
    private static final int STOCK_ORDER_QUANTITY = 1;
    private static final double COMMISSION_RATE = 3;

    private void ReInitVariables() {

        account = Account.builder()
                .balance(ACCOUNT_BALANCE)
                .email(username)
                .build();

        stockPurchaseRequest = StockPurchaseRequest.builder()
                .stockSymbol(stockTypeSombol)
                .quantity(STOCK_ORDER_QUANTITY)
                .build();

        stockType = StockType.builder()
                .symbol(stockTypeSombol)
                .build();

        stockHistory = StockHistory.builder()
                .value(STOCK_VALUE)
                .build();

        stockQuantity = StockQuantity.builder()
                .quantity(STOCK_QUANTITY)
                .build();
    }

    private void MockAll() {

        Mockito.when(accountService.getAccountByEmail(username))
                .thenReturn(account);

        Mockito.when(stockTypeService.getStockType(stockPurchaseRequest.getStockSymbol()))
                .thenReturn(stockType);

        Mockito.when(stockHistoryService.getRecentHistory(stockType))
                .thenReturn(stockHistory);

        Mockito.when(stockQuantityService.getStockQuantity(stockType))
                .thenReturn(stockQuantity);

        Mockito.when(stockAccessiblityService.isStockAccessible(stockType.getId()))
                .thenReturn(true);
    }

    @Test
    public void Should_PurchaseStock_When_ConditionsMet() {

        ReInitVariables();

        MockAll();

        double expectedAccountBalance = account.getBalance()
                - ((STOCK_VALUE * STOCK_ORDER_QUANTITY) + (STOCK_VALUE * STOCK_ORDER_QUANTITY * COMMISSION_RATE / 100));

        transactionService.PurchaseStock(username, stockPurchaseRequest);

        assertEquals(account.getBalance(), expectedAccountBalance);

    }

    @Test
    public void Should_ThrowStockAccessibilityException_When_StockIsClosedToTransaction() {

        ReInitVariables();

        MockAll();

        Mockito.when(stockAccessiblityService.isStockAccessible(stockType.getId()))
                .thenReturn(false);

        assertThrows(StockAccessibilityException.class,
                () -> transactionService.PurchaseStock(username, stockPurchaseRequest));
    }

    @Test
    public void Should_ThrowNotEnoughStockException_When_ThereIsNotEnoughStockToPurchase() {

        ReInitVariables();

        MockAll();

        stockPurchaseRequest.setQuantity(Integer.MAX_VALUE);

        assertThrows(NotEnoughStockException.class,
                () -> transactionService.PurchaseStock(username, stockPurchaseRequest));
    }

    @Test
    public void Should_ThrowNotEnoughBalance_When_NotEnoughBalanceToPurchaseDesiredStock() {

        ReInitVariables();

        MockAll();

        account.setBalance(0);

        assertThrows(NotEnoughBalanceException.class,
                () -> transactionService.PurchaseStock(username, stockPurchaseRequest));
    }

    @Test
    @BeforeEach
    public void Should_SellStock_When_ConditionsMet() {

        ReInitVariables();

        MockAll();

        double expectedAccountBalance = account.getBalance()
                + ((STOCK_VALUE * STOCK_ORDER_QUANTITY) - (STOCK_VALUE * STOCK_ORDER_QUANTITY * COMMISSION_RATE / 100));

        Mockito.when(transactionService.getCurrentQuantity(account,
                stockType.getSymbol()))
                .thenReturn(1);

        StockSellRequest stockSellRequest = new StockSellRequest();
        stockSellRequest.setQuantity(STOCK_ORDER_QUANTITY);
        stockSellRequest.setStockSymbol(stockTypeSombol);

        transactionService.SellStock(username, stockSellRequest);

        assertEquals(account.getBalance(), expectedAccountBalance);
    }

    @Test
    public void Should_ThrowStockAccessibilityException_When_StockIsClosedToTransactionToSell() {

        ReInitVariables();

        MockAll();
        
        Mockito.when(stockAccessiblityService.isStockAccessible(stockType.getId()))
                .thenReturn(false);

        Mockito.when(transactionService.getCurrentQuantity(account,
                stockType.getSymbol()))
                .thenReturn(0);

        StockSellRequest stockSellRequest = new StockSellRequest();
        stockSellRequest.setQuantity(STOCK_ORDER_QUANTITY);
        stockSellRequest.setStockSymbol(stockTypeSombol);

        assertThrows(StockAccessibilityException.class, () -> transactionService.SellStock(username, stockSellRequest));
    }

    @Test
    public void Should_ThrowStockQuantityException_When_AccountDoesNotHaveEnoughStock() {

        ReInitVariables();

        MockAll();

        Mockito.when(transactionService.getCurrentQuantity(account,
                stockType.getSymbol()))
                .thenReturn(0);

        StockSellRequest stockSellRequest = new StockSellRequest();
        stockSellRequest.setQuantity(STOCK_ORDER_QUANTITY);
        stockSellRequest.setStockSymbol(stockTypeSombol);

        assertThrows(NotEnoughStockException.class, () -> transactionService.SellStock(username, stockSellRequest));
    }
}
