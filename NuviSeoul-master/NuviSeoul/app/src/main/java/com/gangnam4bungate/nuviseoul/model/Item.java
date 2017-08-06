package com.gangnam4bungate.nuviseoul.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wsseo on 2017. 7. 6..
 */

public class Item {
    @SerializedName("item")
    List<AreaBaseInfo> mitem;
}
