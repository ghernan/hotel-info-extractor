package com.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

        Document doc = Jsoup.connect("https://www.tripadvisor.com/Hotel_Review-g31310-d239800-Reviews-Hilton_Garden_Inn_Phoenix_Midtown-Phoenix_Arizona.html").get();

        Element reviewCont = doc.body().getElementById("REVIEWS");

        Elements reviews = reviewCont.getElementsByClass("review-container");

        Iterator<Element> elementIterator = reviews.iterator();

        while( elementIterator.hasNext() ){
            Elements titles = elementIterator.next().getElementsByClass("quote");

            System.out.println(titles.get(0).text());
        }
    }
}
