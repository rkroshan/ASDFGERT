package com.rajatv.surajv.roshank.sac.NewsAndNotice;

import java.util.HashMap;

/**
 * Created by CREATOR on 10/28/2017.
 */

public class update {

    private String heading,news;
    private HashMap<String,String> images;

    public HashMap<String, String> getImages() {
        return images;
    }

    public void setImages(HashMap<String, String> images) {
        this.images = images;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

}
