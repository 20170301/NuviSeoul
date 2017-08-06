package com.mystory.commonlibrary.network;

import android.content.Context;
import android.content.res.ObbInfo;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by wsseo on 2017. 6. 30..
 */

public class HttpClientManager {
    private static HttpClientManager mHttpClientManager = null;
    private RequestQueue mRequestQueue;

    public HttpClientManager(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static HttpClientManager getInstance(Context context){
        if(mHttpClientManager == null) {
            mHttpClientManager = new HttpClientManager(context);
        }
        return mHttpClientManager;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public void sendPost(final Object object, String url, final String requestCode){
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            MashupCallback callback = (MashupCallback)object;
                            if(callback != null)
                                callback.onMashupSuccess(response, requestCode);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MashupCallback callback = (MashupCallback)object;
                            if(callback != null)
                                callback.onMashupFail(error, requestCode);
                        }
                    });
            mRequestQueue.add(request);
        }catch(Exception e){

        }
    }

    public void sendGet(final Object object, String url, final String requestCode){
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            MashupCallback callback = (MashupCallback)object;
                            if(callback != null)
                                callback.onMashupSuccess(response, requestCode);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MashupCallback callback = (MashupCallback)object;
                            if(callback != null)
                                callback.onMashupFail(error, requestCode);
                        }
                    });
            mRequestQueue.add(request);
        }catch(Exception e){

        }
    }

}
