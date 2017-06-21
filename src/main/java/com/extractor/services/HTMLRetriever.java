package com.extractor.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
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

        List<WebElement> entries = driver.findElements(By.className("partial_entry"));
        for (WebElement entry:entries) {
            List<WebElement> temp = entry.findElements(By.className("taLnk"));
            if ( temp.size() > 0) {
                WebElement more = entry.findElement(By.className("taLnk"));

                Actions actions = new Actions(driver);

                actions.moveToElement(more).click().perform();

                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.invisibilityOf(more));
                break;
            }
        }
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }

    public Document changePage(int pageNumber) {

        List<WebElement> pages = driver.findElements(By.className("pageNum"));

        int pageTChange = ( pageNumber == pages.size() )? pages.size()-1: pageNumber;

        WebElement pageNum = pages.get(pageTChange);

        pageNum.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("taplc_location_reviews_list_hotels_0"))));
        driver.get(driver.getCurrentUrl());
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }


}
