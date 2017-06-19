package com.extractor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class App {

    public static void main(String[] args) throws Exception {

        URL oracle = new URL("https://www.tripadvisor.com/Hotel_Review-g31310-d239800-Reviews-Hilton_Garden_Inn_Phoenix_Midtown-Phoenix_Arizona.html");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
