package com.stockmarket.sproject.application.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.model.TransactionHistory;
import com.stockmarket.sproject.application.repository.IStockHistoryRepository;
import com.stockmarket.sproject.application.repository.IStockTypeRepository;
import com.stockmarket.sproject.application.repository.ITransactionHistory;
import com.stockmarket.sproject.webscrapper.BasicStockInformation;

@Service
public class StockHistoryService {

    private final IStockHistoryRepository stockHistoryRepository;

    private final IStockTypeRepository stockTypeRepository;

    private final ITransactionHistory transactionHistory;

    private static final int DEFUALT_QUANTITY = 1000;

    public StockHistoryService(IStockHistoryRepository stockHistoryRepository,
            IStockTypeRepository stockTypeRepository,
            ITransactionHistory transactionHistory) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockTypeRepository = stockTypeRepository;
        this.transactionHistory = transactionHistory;

    }

    public void createNewHistory(BasicStockInformation basicStockInformation) {
        StockType stockType = stockTypeRepository.findFirstBySymbol(basicStockInformation.getSymbol());

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

        StockHistory stockHistoryPrevious = stockHistoryRepository
                .findFirstByStockTypeOrderByUpdateTimeAsc(stockType); 

        if(stockHistoryPrevious == null)
            return DEFUALT_QUANTITY;

        int stockQuantityPrevious = stockHistoryPrevious.getQuantity();
        int stockQuantitySpent = 0;
        
        List<TransactionHistory> transactionHistories = transactionHistory.findAllByStockHistory(stockHistoryPrevious);
        
        for (TransactionHistory transactionHistory : transactionHistories) {
            stockQuantitySpent += transactionHistory.getQuantity();
        }
        
        int stockQuantityCurrent = stockQuantityPrevious - stockQuantitySpent;

        return stockQuantityCurrent;
    }

}
