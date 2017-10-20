package com.gangnam4bungate.nuviseoul.config;

import android.graphics.Bitmap;

/**
 * Created by shj89 on 2017-09-16.
 */

public class searchDTO {
    public String contentId;
    public String title;
    public String firstImage;
    public String mapX;
    public String mapY;
    public Bitmap homePage;
    public String overView;

    public searchDTO(String contentId, String title, String firstImage, String mapX, String mapY, Bitmap homePage, String overView) {
        this.contentId = contentId;
        this.title = title;
        this.firstImage = firstImage;
        this.mapX = mapX;
        this.mapY = mapY;
        this.homePage = homePage;
        this.overView = overView;
    }
}
