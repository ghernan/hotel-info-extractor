package com.extractor.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
    private WebDriver driver = new FirefoxDriver();

    public HTMLRetriever(String urlString) {

        try {
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

    public void setContent(Document content) {
        this.content = content;
    }

    public Document prepareCommentHTML() {

        List<WebElement> entries = driver.findElement(By.id("REVIEWS")).findElements(By.className("review-container"));
        for (WebElement entry : entries) {
            List<WebElement> temp = entry.findElements(By.className("partial_entry")).get(0).findElements(By.cssSelector(".taLnk.ulBlueLinks"));
            if (temp.size() > 0) {
                WebElement more;
                more = temp
                        .stream()
                        .filter(element -> {
                            if( "More".equals( element.getText() ) )
                                return true;
                            return false;
                        })
                        .findFirst().get();

                if( more == null )
                    continue;

                WebDriverWait wait = new WebDriverWait(driver, 8);

                clickOnElement(more);

                wait.until(ExpectedConditions.invisibilityOf(more));
                break;
            }
        }
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }

    public Document changePage(int pageNumber) {

        WebElement pageNum = driver.findElement(By.cssSelector(".next.arrowNav"));

        clickOnElement(pageNum);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlContains("or"+(pageNumber*5)));
        driver.navigate().refresh();
//        WebDriverWait waitAfterReLoad = new WebDriverWait(driver, 6);
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("taLnk.ulBlueLinks")));
        String documentString = driver.getPageSource();
        setContent(Jsoup.parse(documentString));
        return Jsoup.parse(documentString);
    }

    private void clickOnElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(" + element.getLocation().x + "," + element.getLocation().y + ")");

        js.executeScript("arguments[0].click();", element);
    }
}
