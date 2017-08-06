package com.gangnam4bungate.nuviseoul.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.gangnam4bungate.nuviseoul.R;
import com.mystory.commonlibrary.network.HttpClientManager;
import com.gangnam4bungate.nuviseoul.config.CODES;

import java.net.URLEncoder;

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

    public void requestAreaBaseListInfo(Object object, String content_type_id){
        try {
            Uri builtUri = Uri.parse(CODES.DefaultDomain + CODES.URLCodes.URL_AREABASELIST)
                    .buildUpon()
                    .appendQueryParameter(CODES.CommonCodes.CONTENTTYPEID, content_type_id)
                    .appendQueryParameter(CODES.CommonCodes.MOBILEOS, "AND")
                    .appendQueryParameter(CODES.CommonCodes.MOBILEAPP, ((Context) object).getString(R.string.app_name))
                    .appendQueryParameter(CODES.CommonCodes._TYPE, "json")
                    .build();

            String url = builtUri.toString() + "&" + CODES.CommonCodes.SERVICEKEY + "=" + CODES.Dev_ServiceKey;

            HttpClientManager.getInstance((Context) object).sendGet(object, url, CODES.RequestCode.REQUEST_AREABASELIST);
        }catch(Exception e){

        }
    }

}
