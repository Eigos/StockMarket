package com.stockmarket.sproject.webscrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
class ScheduledDataFetchingConfig {

    private final WebScrapperService webScrapperService;

    private static final long TASK_PERFORM_DELAY_MS = 15000;

    ScheduledDataFetchingConfig(WebScrapperService webScrapperService){
        this.webScrapperService = webScrapperService;
    }

    @Scheduled(fixedRate = TASK_PERFORM_DELAY_MS)
    public void fetchData(){
        List<BasicStockInformation> dataList = webScrapperService.getDataFromNet();

        webScrapperService.saveData(dataList);

    }

}