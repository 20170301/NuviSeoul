package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;
import com.mystory.commonlibrary.network.MashupCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wsseo on 2017. 7. 9..
 */

public class SearchActivity extends CommonActivity implements MashupCallback {
    ArrayList<String> blogList = new ArrayList<>();
    ArrayList<String> linkList = new ArrayList<>();
    TextView searchTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        //searchTextView = (TextView)findViewById(R.id.searchTextView);
        Intent intent = getIntent();
        String value = intent.getStringExtra("mEt_title_value");
        NetworkManager.getInstance().requestAreaBaseListInfo(this, value);
    }

    @Override
    public void onMashupSuccess(JSONObject object, String requestCode) {
        Log.d(CODES.TAG, "Search " + object.toString());
//        String jsonInfo = object.toString();
//        JsonParser jsonParser = new JsonParser();
        RecyclerView view = (RecyclerView) findViewById(R.id.main_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        try
        {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONObject response = jsonObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject locations = body.getJSONObject("items");
            JSONArray item = locations.getJSONArray("item");
            //

            searchRecyclerView searchRecyclerView = new searchRecyclerView(item);
            view.setLayoutManager(layoutManager);
            view.setAdapter(searchRecyclerView);


//            String titleInfo, linkInfo, descripsionInfo;
//
//            for (int i = 0; i < item.length(); i++)
//            {
//                titleInfo = item.getJSONObject(i).getString("title");
//                String title = titleInfo.replaceAll("<b>", "").replaceAll("</b>","");
//                linkInfo = item.getJSONObject(i).getString("link");
//                descripsionInfo = item.getJSONObject(i).getString("description");
//                String description = descripsionInfo.replaceAll("<b>", "").replaceAll("</b>","");
//
//                String naverFristJuso = "";
//                String naverLastJuso = "";
//                String daumJuso = "";
//
//                searchTextView.append(title + "\n");
//                searchTextView.append(description + "\n");
//
//                if(linkInfo.contains("naver")){
//                    int frist = linkInfo.indexOf("?");
//                    int last = linkInfo.lastIndexOf("=");
//                    naverFristJuso = linkInfo.substring(0,frist);
//                    naverLastJuso = linkInfo.substring(last,linkInfo.length());
//                    searchTextView.append(naverFristJuso +""+naverLastJuso + "\n");
//                } else if(linkInfo.contains("daum")){
//                    daumJuso = linkInfo.replaceAll("\\|","");
//                    searchTextView.append(daumJuso + "\n");
//                }
//
//            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
