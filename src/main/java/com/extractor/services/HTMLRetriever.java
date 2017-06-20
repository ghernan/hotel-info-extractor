package com.extractor.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class HTMLRetriever {

    public static String baseUrl;
    public Document content;
    private WebDriver driver = new ChromeDriver();

    public HTMLRetriever(String urlString)  {

        try{
            setUrl(urlString);
            driver.get(urlString);

        } catch (Exception e) {
            Logger.getLogger("HTMLRetriever").warning(e.toString());
        }
    }



    public String getUrl() {
        return baseUrl;
    }

    public void setUrl(String url) {
        this.baseUrl = url;
    }

    public Document getContent() {
        return content;
    }

    public void setContent(Document content) { this.content = content; }

    public Document prepareCommmentHTML() {

        WebElement more = driver.findElements(By.className("partial_entry")).get(0).findElement(By.className("taLnk"));
        more.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.invisibilityOf(more));
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }

    public Document changePage(int pageNumber) {

        WebElement pageNum = driver.findElements(By.className("pageNum")).get(pageNumber);
        pageNum.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("taplc_location_reviews_list_hotels_0"))));
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }


}
