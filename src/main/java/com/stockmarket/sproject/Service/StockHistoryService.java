package com.stockmarket.sproject.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stockmarket.sproject.model.StockHistory;
import com.stockmarket.sproject.model.StockType;
import com.stockmarket.sproject.repository.IStockHistoryRepository;
import com.stockmarket.sproject.repository.IStockTypeRepository;
import com.stockmarket.sproject.webscrapper.BasicStockInformation;

@Service
public class StockHistoryService {

    private final IStockHistoryRepository stockHistoryRepository;

    private final IStockTypeRepository stockTypeRepository;

    public StockHistoryService(IStockHistoryRepository stockHistoryRepository,
            IStockTypeRepository stockTypeRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockTypeRepository = stockTypeRepository;
    }

    public void createNewHistory(BasicStockInformation basicStockInformation) {
        StockType stockType = stockTypeRepository.findFirstBySymbol(basicStockInformation.getSymbol());

        StockHistory stockHistory = new StockHistory();

        stockHistory.setQuantity(1000);
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

        stockHistory.setQuantity(1000);
        stockHistory.setStockType(stockType);
        stockHistory.setValue(value);

        stockHistoryRepository.save(stockHistory);
    }

}
