package com.gangnam4bungate.nuviseoul.ui.activity;

/**
 * Created by Master on 2017-07-29.
 */

public class RecommendData {

    public int imageId;
    //public String image;
    public String title;
    public String addres;
    public double latitude;
    public double longitude;

    RecommendData(int image, String title, String addres, double latitude, double longitude) {

        this.imageId = image;
        //this.image = image;
        this.title = title;
        this.addres = addres;
        this.latitude = latitude;
        this.longitude = longitude;
}
}
