package com.extractor.managers;

import com.extractor.models.Attraction;
import com.extractor.models.NearBy;
import com.extractor.models.Restaurant;
import com.extractor.models.Review;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public List<NearBy> extractNearby(Element element, String type) {

        List<NearBy> resultList = new ArrayList<NearBy>();
        Elements restaurantsContainers =  element.getElementsByClass(type);
        Elements poiInfos = restaurantsContainers.first().getElementsByClass("poiInfo");
        Iterator<Element> elementIterator = poiInfos.iterator();

        while (elementIterator.hasNext()) {
            NearBy nearBy;
            switch (type) {
                case "eatery":
                    nearBy = new Restaurant();
                    break;
                case "attraction":
                    nearBy = new Attraction();
                    break;
                default:
                    nearBy = new Restaurant();
                    break;
            }

            Element poiInfo = elementIterator.next();
            Elements names = poiInfo.getElementsByClass("poiName");
            Elements ratings = poiInfo.getElementsByClass("ui_bubble_rating");

            nearBy.name = names.first().text();
            nearBy.rating = getRating(ratings.first());

            resultList.add(nearBy);
        }
        return  resultList;

    }

    private String getRating(Element ratingElement) {


        String rating  = ratingElement.attr("class").split(" ")[1].split("_")[1];

        return  (Double.parseDouble(rating)/10)+"";
    }





}
