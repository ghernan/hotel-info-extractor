package com.extractor.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by antoniohernandez on 6/19/17.
 */
public class HTMLRetriever {

    private URL url;
    private Document content;

    public HTMLRetriever(String urlString)  {

        try{

            setContent(Jsoup.connect(urlString).get());

        } catch (Exception e) {
            Logger.getLogger("HTMLRetriever").warning(e.toString());
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Document getContent() {
        return content;
    }

    public void setContent(Document content) {
        this.content = content;
    }
}
