package com.stockmarket.sproject.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.webscrapper.BasicStockInformation;
import com.stockmarket.sproject.webscrapper.BasicWebScrapper;
import com.stockmarket.sproject.webscrapper.IWebScrapper;
import com.stockmarket.sproject.webscrapper.WebScrapperService;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    
    private final WebScrapperService webScrapperService;

    public TestController(WebScrapperService webScrapperService){
        this.webScrapperService = webScrapperService;
    }

    @GetMapping(value = "greet")
    public ResponseEntity<String> greetMessage(){
        return ResponseEntity.ok("Hello there!");
    }

    @GetMapping(value = "greet-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> greetMessageAdmin(){
        return ResponseEntity.ok("Hello there!");
    }


    @GetMapping(value = "web-scrap")
    public ResponseEntity<List<BasicStockInformation>> getWebPage(){

        IWebScrapper<BasicStockInformation> webScrapper = new BasicWebScrapper();

        List<BasicStockInformation> basicStockInformations = webScrapper.getData();
        
        return ResponseEntity.ok(basicStockInformations);
    }

    @GetMapping(value = "web-scrap-async")
    public ResponseEntity<List<BasicStockInformation>> getWebPageAsync(){

        List<BasicStockInformation> basicStockInformations = webScrapperService.getDataFromNet();

        return ResponseEntity.ok(basicStockInformations);
    }

    @GetMapping(value = "web-scrap-async-db")
    public ResponseEntity<List<BasicStockInformation>> getWebPageDB(){

        List<BasicStockInformation> basicStockInformations = webScrapperService.getDataFromNet();

        webScrapperService.saveData(basicStockInformations);

        return ResponseEntity.ok(basicStockInformations);
    }

}
