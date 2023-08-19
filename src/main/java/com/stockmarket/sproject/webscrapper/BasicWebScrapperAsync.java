package com.stockmarket.sproject.webscrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BasicWebScrapperAsync extends BasicWebScrapper {


    @Override
    public List<BasicStockInformation> getData() {

        ArrayList<BasicStockInformation> basicStockInformations = new ArrayList<BasicStockInformation>();
        ArrayList<CompletableFuture<Void>> asyncTasks = new ArrayList<CompletableFuture<Void>>();
        
        try {
            Elements stockElements = getStockElements();
            
            for (Element element : stockElements) 
                asyncTasks.add(asyncMethod(basicStockInformations, element));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
            asyncTasks.toArray(new CompletableFuture[0])
        );

        try {
            allOf.get(); // Wait for all tasks to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return basicStockInformations;
    }

     public static CompletableFuture<Void> asyncMethod(List<BasicStockInformation> stockList, Element element) {
        return CompletableFuture.runAsync(() -> {
            
            BasicStockInformation basicStockInformation;
            try {
                basicStockInformation = new BasicStockInformation(
                            getSymbol(element),
                            getElementDescription(element),
                            Double.valueOf(getValue(element)));
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
                return;
            }

            synchronized (stockList) {
                stockList.add(basicStockInformation);
                System.out.println(basicStockInformation);
            }
        });
    }
    
}
