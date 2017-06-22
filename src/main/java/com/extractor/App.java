package com.extractor;

import com.extractor.managers.HTMLExtractor;
import com.extractor.models.Attraction;
import com.extractor.models.Restaurant;
import com.extractor.models.Review;
import com.extractor.services.HTMLRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class App {

    public static void main(String[] args) throws Exception {
        FileWriter reviewsWriter, restaurantsWriter, attractionsWriter;
        BufferedWriter reviewsBuffer, restaurantsBuffer, attractionsBuffer;
        HashMap<String, String> hotels = getHotelHashmap();
        String resultHotels = "";
        List<Review> reviews = new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();
        List<Attraction> attractions = new ArrayList<>();

        //TODO implement foreach on hotel hashmap
        List<String> hotelList = new ArrayList<>();

        hotels.forEach((key, value) -> hotelList.add(value));

        for (String hotelUrl : hotelList) {
            HTMLRetriever retriever = new HTMLRetriever(hotelUrl);
            HTMLExtractor extractor = new HTMLExtractor();

            for (int i = 0; i < 20; i++) {

                if (i > 0) {

                    retriever.changePage(i);
                }
                Element reviewCont = retriever.prepareCommentHTML().body().getElementById("REVIEWS");
                reviews.addAll(extractor.extractReviews(reviewCont));
            }
            Element nearby = retriever.content.body().getElementById("LOCATION_TAB");
            restaurants.addAll((List<Restaurant>) (List<?>) extractor.extractNearby(nearby, "eatery"));
            attractions.addAll((List<Attraction>) (List<?>) extractor.extractNearby(nearby, "attraction"));

            resultHotels = getJsonFromList(reviews);

            System.out.println("Otra cosa");
        }


        reviewsWriter = new FileWriter("Reviews.json");
        attractionsWriter = new FileWriter("Attractions.json");
        restaurantsWriter = new FileWriter("Restaurants.json");
        reviewsBuffer = new BufferedWriter(reviewsWriter);
        restaurantsBuffer = new BufferedWriter(restaurantsWriter);
        attractionsBuffer = new BufferedWriter(attractionsWriter);
        try{

            reviewsBuffer.write(resultHotels);
            restaurantsBuffer.write(getJsonFromList(restaurants));
            attractionsBuffer.write(getJsonFromList(attractions));


        } catch (IOException e) {
            System.out.println("");
        }
        restaurantsBuffer.close();
        restaurantsWriter.close();
        reviewsBuffer.close();
        reviewsWriter.close();
        attractionsBuffer.close();
        attractionsWriter.close();

        System.out.println("Lo que sea");
    }

    public static HashMap<String, String> getHotelHashmap() {
        Properties properties = new Properties();
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream("hotels.properties"));
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Cannot load file " + e.getMessage());
        }
        HashMap<String, String> mymap = new HashMap<String, String>((Map) properties);

        return mymap;
    }

    public static String getJsonFromList(List<?> list){
        ObjectMapper mapper = new ObjectMapper();

        String result = "";

        try {
            result = result + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
