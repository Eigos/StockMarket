package com.stockmarket.sproject.webscrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BasicWebScrapper implements IWebScrapper<BasicStockInformation> {

    public BasicWebScrapper() {
    }

    @Override
    public List<BasicStockInformation> getData() {

        Document document;
        ArrayList<BasicStockInformation> basicStockInformations = new ArrayList<BasicStockInformation>();
        try {
            String baseUrl = "https://bigpara.hurriyet.com.tr";
            document = Jsoup
                    .connect(baseUrl + "/borsa/canli-borsa/")
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .header("Accept-Language", "*")
                    .get();

            Element elementBase = document.getElementById("sortable");
            Elements elements = elementBase.getElementsByTag("ul");

            for (Element element : elements) {

                String elemSymbol = element.attr("data-symbol");
                String elemValue = element.getElementsByClass("cell048 node-c").first().text().replace(".", "").replace(",", ".");
                String elemLink = element.getElementsByAttributeValue("target", "_blank").first().attr("href");
                String elemDescription;
                
                
                {
                    Document documentForDescriptiom = Jsoup
                    .connect(baseUrl + elemLink)
                    .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                        .header("Accept-Language", "*")
                        .get();
                        
                    elemDescription = documentForDescriptiom.getElementsByClass("pageTitle mBot10").first().text();
                }
                
                
                basicStockInformations.add(new BasicStockInformation(
                    elemSymbol,
                    elemDescription,
                    Double.parseDouble(elemValue))
                    );

                System.out.println("Elem Symbol: " + elemSymbol);
                System.out.println("Elem Value: " + elemValue);
                System.out.println("Elem ref link:" + elemLink);
                System.out.println("Elem final link: " + baseUrl + elemLink);
                System.out.println("Elem decription: " + elemDescription);
                System.out.println("----");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return basicStockInformations;
    }

}
