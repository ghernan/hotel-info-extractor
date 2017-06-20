package com.extractor;

import com.extractor.managers.HTMLExtractor;
import com.extractor.models.Attraction;
import com.extractor.models.Restaurant;
import com.extractor.models.Review;
import com.extractor.services.HTMLRetriever;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Iterator;
import java.util.List;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class App {

    public static void main(String[] args) throws Exception {

        HTMLRetriever retriever = new HTMLRetriever("https://www.tripadvisor.com/Hotel_Review-g31310-d239800-Reviews-Hilton_Garden_Inn_Phoenix_Midtown-Phoenix_Arizona.html");
        HTMLExtractor extractor = new HTMLExtractor();
        Element reviewCont =  retriever.prepareCommmentHTML().body().getElementById("REVIEWS");
        Element nearby =  retriever.content.body().getElementById("LOCATION_TAB");
        List<Review> reviews = extractor.extractReviews(reviewCont);
        List<Restaurant> restaurants = (List<Restaurant>)(List<?>) extractor.extractNearby(nearby, "eatery");
        List<Attraction> attractions = (List<Attraction>)(List<?>) extractor.extractNearby(nearby, "attraction");

        retriever.changePage(2);
        Element dummyDoc = retriever.prepareCommmentHTML().body().getElementById("REVIEWS");
        List<Review> reviews2 = extractor.extractReviews(dummyDoc);




        System.out.println("Lo que sea");


    }
}
