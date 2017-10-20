package com.gangnam4bungate.nuviseoul.network;

import android.content.Context;
import android.net.Uri;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.mystory.commonlibrary.network.HttpClientManager;

/**
 * Created by wsseo on 2017. 6. 27..
 */

public class NetworkManager {
    private static NetworkManager mNetworkManager;
    public static NetworkManager getInstance(){
        if(mNetworkManager == null)
            mNetworkManager = new NetworkManager();
        return mNetworkManager;
    }

    public void requestAreaBaseListInfo(Object object, String keyword){
        try {
            Uri builtUri = Uri.parse(CODES.DefaultDomain + CODES.URLCodes.URL_AREABASELIST)
                    .buildUpon()
                    .appendQueryParameter(CODES.CommonCodes.KEYWORD, keyword)
                    .appendQueryParameter(CODES.CommonCodes.MOBILEOS, "AND")
                    .appendQueryParameter(CODES.CommonCodes.MOBILEAPP, ((Context) object).getString(R.string.app_name))
                    .appendQueryParameter(CODES.CommonCodes._TYPE, "json")
                    .build();

            String url = builtUri.toString() + "&" + CODES.CommonCodes.SERVICEKEY + "=" + CODES.Dev_ServiceKey;

            HttpClientManager.getInstance((Context) object).sendGet(object, url, CODES.RequestCode.REQUEST_AREABASELIST);
        }catch(Exception e){

        }
    }

    public void requestAreaBaseDetailListInfo(Object object, String contentid){
        try {
            Uri builtUri = Uri.parse("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=" + CODES.Dev_ServiceKey + "&contentId="+ contentid +"&defaultYN=Y&MobileOS=AND&overviewYN=Y&MobileApp=" + ((Context) object).getString(R.string.app_name));
            String url = builtUri.toString();

            HttpClientManager.getInstance((Context) object).sendGet(object, url, CODES.RequestCode.REQUEST_AREABASEDETAILLIST);
        }catch (Exception e){

        }
    }

    public void requestNaverSearchInfo(Object object, String query){
        try {
            Uri builtUri = Uri.parse(CODES.NaverDomain + CODES.URLCodes.URL_SEARCH)
                    .buildUpon()
                    .build();

            String url = builtUri.toString() + query;

            HttpClientManager.getInstance((Context) object).sendGet_naver(object, url, CODES.RequestCode.REQUEST_SEARCH, CODES.NAVER_CLIENT_ID, CODES.NAVER_CLIENT_SECRET);
        }catch(Exception e){

        }
    }

    public void requsetRecommendLocationInfo(Object object)
    {
        String key = "Wasn0OGY43ReOgSy5nz8ZNURGTw4Y5MRPt%2B1rJw0xZCEbQG07s6n5hjqHHqzzIRBCP8U1H64Q0RIgAZPRWSPlA%3D%3D";


        try {
            Uri builtUri = Uri.parse("http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey=" + key +
                    "&mapX=126.981611&mapY=37.568477&radius=10000&pageNo=1&numOfRows=14&listYN=Y&arrange=B&contentTypeId=14&MobileOS=AND&MobileApp=MyApplication&_type=json");

            String url = builtUri.toString();

            HttpClientManager.getInstance((Context) object).sendGet(object, url, "");

        }catch(Exception e){

        }
    }
}
