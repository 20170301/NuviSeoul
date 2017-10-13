package com.gangnam4bungate.nuviseoul.data;

import java.util.Date;

/**
 * Created by wsseo on 2017. 9. 23..
 */

public class PlanDetailData {
    int planid;
    Date startdate;
    Date enddate;
    String placename;
    int pathseq;
    double latitude;
    double longitude;

    public int getPlanid() {
        return planid;
    }

    public Date getStartDate() {
        return startdate;
    }

    public Date getEndDate() { return enddate; }

    public String getPlacename() {
        return placename;
    }

    public int getPathseq() {
        return pathseq;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setPlanid(int planid) {
        this.planid = planid;
    }

    public void setStartDate(Date datetime) {
        this.startdate = datetime;
    }

    public void setEndDate(Date datetime) { this.enddate = datetime;}

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public void setPathseq(int pathseq) {
        this.pathseq = pathseq;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
