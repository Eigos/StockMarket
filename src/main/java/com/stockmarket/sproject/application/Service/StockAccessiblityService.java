package com.stockmarket.sproject.application.Service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.repository.IStockTypeRepository;

@Service
public class StockAccessiblityService {

    Map<Integer, Boolean> transactionAccessiblityMap;

    IStockTypeRepository stockTypeRepository;

    StockAccessiblityService(IStockTypeRepository stockTypeRepository) {
        this.stockTypeRepository = stockTypeRepository;

        transactionAccessiblityMap = new HashMap<>();
    }

    public void InitTransactionAccessiblityMap() {
        stockTypeRepository.findAll().forEach(stockType -> transactionAccessiblityMap.put(stockType.getId(), true));
    }

    public void AppendMissing() {
        stockTypeRepository.findAll().forEach(stockType -> {
            if (!transactionAccessiblityMap.containsKey(stockType.getId()))
                transactionAccessiblityMap.put(stockType.getId(), true);
        });
    }

    private Boolean doesKeyExist(int stockId) {
        return transactionAccessiblityMap.containsKey(stockId);
    }

    private int getStockId(String symbol) {
        return stockTypeRepository.findFirstBySymbol(symbol).getId();
    }

    public Boolean isStockAccessible(int stockId) {

        // If given key does not exist
        if (!doesKeyExist(stockId))
            return false;

        return transactionAccessiblityMap.get(stockId);
    }

    public Boolean isStockAccessible(String symbol) {
        return isStockAccessible(getStockId(symbol));
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
