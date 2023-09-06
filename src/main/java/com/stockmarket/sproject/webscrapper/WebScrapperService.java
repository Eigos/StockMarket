package com.stockmarket.sproject.webscrapper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stockmarket.sproject.application.Service.StockHistoryService;
import com.stockmarket.sproject.application.Service.StockQuantityService;
import com.stockmarket.sproject.application.model.StockQuantity;
import com.stockmarket.sproject.application.model.StockType;
import com.stockmarket.sproject.application.repository.IStockTypeRepository;

@Service
public class WebScrapperService {

    private final IWebScrapper<BasicStockInformation> webScrapperAsync;

    private final IStockTypeRepository stockTypeRepository;

    private final StockHistoryService stockHistoryService;

    private final StockQuantityService stockQuantityService;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int BATCH_SIZE;

    @PersistenceContext
    private EntityManager entityManager;

    public WebScrapperService(IWebScrapper<BasicStockInformation> webScrapperAsync,
            IStockTypeRepository stockTypeRepository,
            StockHistoryService stockHistoryService,
            StockQuantityService stockQuantityService) {

        this.webScrapperAsync = webScrapperAsync;
        this.stockTypeRepository = stockTypeRepository;
        this.stockHistoryService = stockHistoryService;
        this.stockQuantityService = stockQuantityService;
    }

    public List<BasicStockInformation> getDataFromNet() {
        return webScrapperAsync.getData();
    }

    @Transactional
    public void saveData(List<BasicStockInformation> stockList) {

        // TO-DO: Optimization issue. Method works too slow.

        List<StockType> stockTypes = new ArrayList<>();

        for (BasicStockInformation stock : stockList) {
            StockType stockType = new StockType();
            stockType.setName(stock.getDescription());
            stockType.setSymbol(stock.getSymbol());
            if (!stockTypeRepository.existsBySymbol(stockType.getSymbol()))
                stockTypes.add(stockType);

        }

        /*
        for (int i = 0; i < stockTypes.size(); i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(stockTypes.get(i));
        }
        */

        stockTypeRepository.saveAll(stockTypes);    
        

        // TO-DO : Optimize
        stockHistoryService.createNewHistory(stockList);

        // TO-DO : Optimize
        for (StockType stockType : stockTypes) {
            StockQuantity.builder()
                    .stockType(stockType)
                    .quantity(StockQuantityService.DEFUALT_QUANTITY);

            stockQuantityService.UpdateStockQuantity(stockType);
        }

    }

    public void saveData(BasicStockInformation stock) {

        // TO-DO: Use a mapper

        StockType stockType = new StockType();
        stockType.setName(stock.getDescription());
        stockType.setSymbol(stock.getSymbol());

        if (stockTypeRepository.findFirstBySymbol(stock.getSymbol()) == null) {
            stockTypeRepository.save(stockType);
            System.out.println("Saved: " + stockType);
        }

        stockHistoryService.createNewHistory(stock);

    }

}
