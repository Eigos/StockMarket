package com.stockmarket.sproject.application.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StockAccessiblityService {

    private final StockTypeService stockTypeService;
    
    private Map<Integer, Boolean> transactionAccessiblityMap;

    StockAccessiblityService(StockTypeService stockTypeService) {
        this.stockTypeService = stockTypeService;

        transactionAccessiblityMap = new HashMap<>();
    }

    public void InitTransactionAccessiblityMap() {
        stockTypeService.getAll().forEach(stockType -> transactionAccessiblityMap.put(stockType.getId(), true));
    }

    public void AppendMissing() {
        stockTypeService.getAll().forEach(stockType -> {
            if (!transactionAccessiblityMap.containsKey(stockType.getId()))
                transactionAccessiblityMap.put(stockType.getId(), true);
        });
    }

    private Boolean doesKeyExist(int stockId) {
        return transactionAccessiblityMap.containsKey(stockId);
    }

    public Boolean isStockAccessible(int stockId) {

        // If given key does not exist
        if (!doesKeyExist(stockId))
            return false;

        return transactionAccessiblityMap.get(stockId);
    }

    public Boolean isStockAccessible(String symbol) {
        return isStockAccessible(stockTypeService.getStockTypeId(symbol));
    }

    public void PauseAll() {
        transactionAccessiblityMap.replaceAll((key, val) -> val = false);
    }

    public void ResumeAll() {
        transactionAccessiblityMap.replaceAll((key, val) -> val = true);
    }

    public void Resume(int stockId) {
        // If given key does not exist
        if (!doesKeyExist(stockId))
            return;

        transactionAccessiblityMap.replace(stockId, true);
    }

    public void Pause(int stockId) {
        // If given key does not exist
        if (!doesKeyExist(stockId))
            return;

        transactionAccessiblityMap.replace(stockId, false);
    }

}
