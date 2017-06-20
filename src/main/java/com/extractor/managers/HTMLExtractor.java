package com.extractor.managers;

import com.extractor.models.Review;


import org.jsoup.nodes.Document;
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

            Elements reviewsList = elementIterator.next().getElementsByClass("partial_entry");

            Review review = new Review();

            Elements titles = elementIterator.next().getElementsByClass("quote");
            Elements users = elementIterator.next().getElementsByClass("user");

            review.title = titles.first().text();
            review.user = users.first().text();
            review.content = reviewsList.text();

            resultList.add(review);
        }

        return resultList;
    }


}
