package com.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class App {

    public static void main(String[] args) throws Exception {

        WebDriver webDriver;

        webDriver = new ChromeDriver();

        String baseUrl = "https://www.tripadvisor.com/Hotel_Review-g31310-d239800-Reviews-Hilton_Garden_Inn_Phoenix_Midtown-Phoenix_Arizona.html";

        webDriver.get(baseUrl);

        WebElement more = webDriver.findElements(By.className("partial_entry")).get(0).findElement(By.className("taLnk"));

        more.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 5);

        wait.until(ExpectedConditions.invisibilityOf(more));

        String page = webDriver.getPageSource();

        Document doc = Jsoup.parse(page);

        Element reviewCont = doc.body().getElementById("REVIEWS");

        Elements reviews = reviewCont.getElementsByClass("review-container");

        Iterator<Element> elementIterator = reviews.iterator();

        while( elementIterator.hasNext() ){
            Elements titles = elementIterator.next().getElementsByClass("partial_entry");

            System.out.println(titles.first().text());
        }
    }
}
