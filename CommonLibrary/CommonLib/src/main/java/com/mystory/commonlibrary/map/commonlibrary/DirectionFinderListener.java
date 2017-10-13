package com.mystory.commonlibrary.map.commonlibrary;

/**
 * Created by choi on 2017-08-16.
 */


import java.util.List;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> routes);
}