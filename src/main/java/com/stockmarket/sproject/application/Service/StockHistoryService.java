package com.stockmarket.sproject.application.Service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.exception_handler.EntityNotFoundException;
import com.stockmarket.sproject.application.exception_handler.custom_exceptions.StockTypeDoesNotExistException;
import com.stockmarket.sproject.application.model.StockHistory;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.repository.IStockHistoryRepository;
import com.stockmarket.sproject.webscrapper.BasicStockInformation;

@Service
public class StockHistoryService {

    private final IStockHistoryRepository stockHistoryRepository;

    private final StockTypeService stockTypeService;

    private final StockQuantityService stockQuantityService;

    public StockHistoryService(IStockHistoryRepository stockHistoryRepository,
            StockTypeService stockTypeService,
            @Lazy TransactionService transactionService,
            StockQuantityService stockQuantityService) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockTypeService = stockTypeService;
        this.stockQuantityService = stockQuantityService;
    }
    

    public void createNewHistory(BasicStockInformation basicStockInformation) {
        StockType stockType = stockTypeService.getStockType(basicStockInformation.getSymbol());

        StockHistory stockHistory = new StockHistory();

        int currentQuantity = -1;

        try {
            currentQuantity = calculateCurrentStockQuantity(stockType);
        } catch (StockTypeDoesNotExistException e) {
            currentQuantity = StockQuantityService.DEFUALT_QUANTITY;
        }

        stockHistory.setQuantity(currentQuantity);
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

        stockHistory.setQuantity(StockQuantityService.DEFUALT_QUANTITY);
        stockHistory.setStockType(stockType);
        stockHistory.setValue(value);

        stockHistoryRepository.save(stockHistory);
    }

    public int calculateCurrentStockQuantity(StockType stockType) throws StockTypeDoesNotExistException{
        return stockQuantityService.getStockQuantity(stockType).getQuantity();
    }

    public StockHistory getRecentHistory(StockType stockType) throws RuntimeException{
        return stockHistoryRepository
                .findFirstByStockTypeOrderByUpdateTimeDesc(stockType)
                .orElseThrow(() -> new EntityNotFoundException(StockHistory.class, "stockType", stockType.getSymbol()));

    }

    public StockHistory getHistoryById(int id){
        return stockHistoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(StockHistory.class, "stockType", String.valueOf(id)));
    }

}
