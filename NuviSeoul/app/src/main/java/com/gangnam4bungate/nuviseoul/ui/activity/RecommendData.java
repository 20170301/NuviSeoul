package com.gangnam4bungate.nuviseoul.ui.activity;

/**
 * Created by Master on 2017-07-29.
 */

public class RecommendData {


    public int imageId;
    public String text;
    public double latitude;
    public double longitude;

    RecommendData(int imageId, String text, double latitude, double longitude) {

        this.imageId = imageId;
        this.text=text;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
