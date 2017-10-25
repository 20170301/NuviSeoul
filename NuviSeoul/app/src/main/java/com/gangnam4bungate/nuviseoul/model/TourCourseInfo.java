package com.gangnam4bungate.nuviseoul.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wsseo on 2017. 7. 6..
 */

public class TourCourseInfo {
    @SerializedName("areacode")
    String areacode;
    @SerializedName("cat1")
    String cat1;
    @SerializedName("cat2")
    String cat2;
    @SerializedName("cat3")
    String cat3;
    @SerializedName("contentid")
    String contentid;
    @SerializedName("contenttypeid")
    String contenttypeid;
    @SerializedName("createdtime")
    String createdtime;
    @SerializedName("firstimage")
    String firstimage;
    @SerializedName("firstimage2")
    String firstimage2;
    @SerializedName("mapx")
    String mapx;
    @SerializedName("mapy")
    String mapy;
    @SerializedName("mlevel")
    String mlevel;
    @SerializedName("modifiedtime")
    String modifiedtime;
    @SerializedName("readcount")
    String readcount;
    @SerializedName("sigungucode")
    String sigungucode;
    @SerializedName("title")
    String title;

    public String getAreacode() {
        return areacode;
    }

    public String getCat1() {
        return cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public String getContentid() {
        return contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public String getMapx() {
        return mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public String getMlevel() {
        return mlevel;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public String getReadcount() {
        return readcount;
    }

    public String getSigungucode() {
        return sigungucode;
    }

    public String getTitle() {
        return title;
    }
}
