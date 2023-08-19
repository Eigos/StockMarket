package com.stockmarket.sproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.webscrapper.BasicStockInformation;
import com.stockmarket.sproject.webscrapper.BasicWebScrapper;
import com.stockmarket.sproject.webscrapper.BasicWebScrapperAsync;
import com.stockmarket.sproject.webscrapper.IWebScrapper;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    
    @GetMapping(value = "greet")
    public ResponseEntity<String> greetMessage(){
        return ResponseEntity.ok("Hello there!");
    }

    @GetMapping(value = "webscrap")
    public ResponseEntity<List<BasicStockInformation>> getWebPage(){

        IWebScrapper<BasicStockInformation> webScrapper = new BasicWebScrapper();

        List<BasicStockInformation> basicStockInformations = webScrapper.getData();
        
        return ResponseEntity.ok(basicStockInformations);
    }

    @GetMapping(value = "webscrapasync")
    public ResponseEntity<List<BasicStockInformation>> getWebPageAsync(){

        IWebScrapper<BasicStockInformation> webScrapper = new BasicWebScrapperAsync();

        List<BasicStockInformation> basicStockInformations = webScrapper.getData();
        
        return ResponseEntity.ok(basicStockInformations);
    }

}
