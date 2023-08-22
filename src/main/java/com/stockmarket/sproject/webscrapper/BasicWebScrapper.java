package com.stockmarket.sproject.webscrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import lombok.NoArgsConstructor;

@Component("webScrapper")
@NoArgsConstructor
public class BasicWebScrapper implements IWebScrapper<BasicStockInformation> {

    private static final String baseUrl = "https://bigpara.hurriyet.com.tr";

    @Override
    public List<BasicStockInformation> getData() {

        List<BasicStockInformation> basicStockInformations = new ArrayList<BasicStockInformation>();

        try {
            
            Elements stockElements = getStockElements();

            for (Element element : stockElements) {

                String elemSymbol = getSymbol(element);
                String elemValue = getValue(element);
                String elemDescription = getElementDescription(element);


                basicStockInformations.add(new BasicStockInformation(
                        elemSymbol,
                        elemDescription,
                        Double.parseDouble(elemValue)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return basicStockInformations;
    }

    protected Elements getStockElements() throws IOException {
        Document document;
        document = Jsoup
                .connect(baseUrl + "/borsa/canli-borsa/")
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();

        Element elementBase = document.getElementById("sortable");
        return elementBase.getElementsByTag("ul");
    }

    protected static String getSymbol(Element element) {
        return element.attr("data-symbol");
    }

    protected static String getValue(Element element) {
        return element.getElementsByClass("cell048 node-c")
                .first()
                .text()
                .replace(".", "")
                .replace(",", ".");
    }

    protected static String getDescriptionLink(Element element) {
        return element.getElementsByAttributeValue("target", "_blank")
                .first()
                .attr("href");
    }

    protected static String getElementDescription(Element element) throws IOException{
        Document documentForDescriptiom = Jsoup
                            .connect(baseUrl + getDescriptionLink(element))
                            .userAgent(
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                            .header("Accept-Language", "*")
                            .get();

        return documentForDescriptiom.getElementsByClass("pageTitle mBot10").first().text();
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
