package com.mystory.commonlibrary.map;

/**
 * Created by choi on 2017-08-16.
 */

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    public String Index;
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}