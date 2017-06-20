package com.extractor.managers;

import com.extractor.models.Restaurant;
import com.extractor.models.Review;


import com.extractor.services.HTMLRetriever;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class HTMLExtractor {


    public List<Review> extractReviews(Element element) {

        List<Review> resultList = new ArrayList<Review>();

        Elements reviews = element.getElementsByClass("review-container");

        Iterator<Element> elementIterator = reviews.iterator();

        while( elementIterator.hasNext() ){

            Review review = new Review();
            Element reviewContainer = elementIterator.next();
            Elements users = reviewContainer.getElementsByClass("username");
            Elements reviewsList = reviewContainer.getElementsByClass("partial_entry");
            Elements titles = reviewContainer.getElementsByClass("quote");
            Elements dates = reviewContainer.getElementsByClass("ratingdate");
            Elements ratings = reviewContainer.getElementsByClass("ui_bubble_rating");
            review.title = titles.first().text();
            review.user = users.first().text();
            review.date = dates.first().text();
            review.content = reviewsList.text();
            review.rating = getRating(ratings.first());
            resultList.add(review);
        }

        return resultList;
    }

    public List<Restaurant> extractRestaurants(Element element) {

        List<Restaurant> resultList = new ArrayList<Restaurant>();
        Elements restaurantsContainers =  element.getElementsByClass("eatery");
        Elements poiInfos = restaurantsContainers.first().getElementsByClass("poiInfo");
        Iterator<Element> elementIterator = poiInfos.iterator();

        while (elementIterator.hasNext()) {

            Restaurant restaurant = new Restaurant();
            Element poiInfo = elementIterator.next();
            Elements names = poiInfo.getElementsByClass("poiName");
            Elements ratings = poiInfo.getElementsByClass("ui_bubble_rating");

            restaurant.name = names.first().text();
            restaurant.rating = getRating(ratings.first());

            resultList.add(restaurant);
        }
        return  resultList;

    }

    private String getRating(Element ratingElement) {


        String rating  = ratingElement.attr("class").split(" ")[1].split("_")[1];

        return  (Double.parseDouble(rating)/10)+"";
    }





}
