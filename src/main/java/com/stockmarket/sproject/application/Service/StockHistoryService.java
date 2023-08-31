package com.stockmarket.sproject.application.Service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.model.TransactionHistory;
import com.stockmarket.sproject.application.repository.IStockHistoryRepository;
import com.stockmarket.sproject.application.repository.IStockTypeRepository;
import com.stockmarket.sproject.application.repository.ITransactionHistoryRepository;
import com.stockmarket.sproject.webscrapper.BasicStockInformation;

@Service
public class StockHistoryService {

    private final IStockHistoryRepository stockHistoryRepository;

    private final StockTypeService stockTypeService;

    private TransactionService transactionService;

    private static final int DEFUALT_QUANTITY = 1000;

    public StockHistoryService(IStockHistoryRepository stockHistoryRepository,
            StockTypeService stockTypeService,
            @Lazy TransactionService transactionService) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockTypeService = stockTypeService;
        this.transactionService = transactionService;
    }
    

    public void createNewHistory(BasicStockInformation basicStockInformation) {
        StockType stockType = stockTypeService.getStockType(basicStockInformation.getSymbol());

        StockHistory stockHistory = new StockHistory();

        stockHistory.setQuantity(calculateCurrentStockQuantity(stockType));
        stockHistory.setStockType(stockType);
        stockHistory.setValue(basicStockInformation.getValue());

        stockHistoryRepository.save(stockHistory);
    }

    public void createNewHistory(List<BasicStockInformation> basicStockInformations) {

        for (BasicStockInformation basicStockInformation : basicStockInformations) {
            createNewHistory(basicStockInformation);
        }
    }

    public void createNewHistory(StockType stockType, double value) {

        StockHistory stockHistory = new StockHistory();

        stockHistory.setQuantity(DEFUALT_QUANTITY);
        stockHistory.setStockType(stockType);
        stockHistory.setValue(value);

        stockHistoryRepository.save(stockHistory);
    }

    public int calculateCurrentStockQuantity(StockType stockType) {

        StockHistory stockHistoryPrevious = null;

        try {
            stockHistoryPrevious = getRecentHistory(stockType); 
        } catch (RuntimeException e) {
            System.out.println(e.getMessage()); // TO-DO : Use Logger
        }

        if(stockHistoryPrevious == null)
            return DEFUALT_QUANTITY;

        int stockQuantityPrevious = stockHistoryPrevious.getQuantity();
        int stockQuantitySpent = 0;
        
        List<TransactionHistory> transactionHistories =  transactionService.getAll(stockHistoryPrevious);
        
        for (TransactionHistory transactionHistory : transactionHistories) {
            stockQuantitySpent += transactionHistory.getQuantity();
        }
        
        int stockQuantityCurrent = stockQuantityPrevious - stockQuantitySpent;

        return stockQuantityCurrent;
    }

    public StockHistory getRecentHistory(StockType stockType) throws RuntimeException{
        return stockHistoryRepository
                .findFirstByStockTypeOrderByUpdateTimeAsc(stockType)
                .orElseThrow(() -> new EntityNotFoundException(StockHistory.class, "stockType", stockType.getSymbol()));

    }


}
