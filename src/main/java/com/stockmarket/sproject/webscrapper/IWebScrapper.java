package com.stockmarket.sproject.webscrapper;

import java.util.List;

public interface IWebScrapper <T> {
    
    List<T> getData();

}
