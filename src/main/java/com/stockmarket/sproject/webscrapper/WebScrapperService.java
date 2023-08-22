package com.stockmarket.sproject.webscrapper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stockmarket.sproject.Service.StockHistoryService;
import com.stockmarket.sproject.model.StockType;
import com.stockmarket.sproject.repository.IStockTypeRepository;

@Service
public class WebScrapperService {

    private final IWebScrapper<BasicStockInformation> webScrapperAsync;

    private final IStockTypeRepository stockTypeRepository;

    private final StockHistoryService stockHistoryService;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int BATCH_SIZE;

    @PersistenceContext
    private EntityManager entityManager;

    public WebScrapperService(IWebScrapper<BasicStockInformation> webScrapperAsync,
            IStockTypeRepository stockTypeRepository,
            StockHistoryService stockHistoryService) {

        this.webScrapperAsync = webScrapperAsync;
        this.stockTypeRepository = stockTypeRepository;
        this.stockHistoryService = stockHistoryService;
    }

    public List<BasicStockInformation> getDataFromNet() {
        return webScrapperAsync.getData();
    }

    @Transactional
    public void saveData(List<BasicStockInformation> stockList) {

        //TO-DO: Optimization issue. Method works too slow.

        List<StockType> stockTypes = new ArrayList<>();

        for (BasicStockInformation stock : stockList) {
            StockType stockType = new StockType();
            stockType.setName(stock.getDescription());
            stockType.setSymbol(stock.getSymbol());
            if (!stockTypeRepository.existsBySymbol(stockType.getSymbol()))
                stockTypes.add(stockType);
            
        }


        for (int i = 0; i < stockTypes.size(); i++) {
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(stockTypes.get(i));
        }
        
        stockHistoryService.createNewHistory(stockList);
        
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
