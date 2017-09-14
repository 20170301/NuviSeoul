package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;
import com.google.gson.JsonParser;
import com.mystory.commonlibrary.network.MashupCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
        setContentView(R.layout.activity_search);
        searchTextView = (TextView)findViewById(R.id.searchTextView);
        Intent intent = getIntent();
        String value = intent.getStringExtra("editTextValue");
        NetworkManager.getInstance().requestNaverSearchInfo(this, value);
    }

    @Override
    public void onMashupSuccess(JSONObject object, String requestCode) {
        Log.d(CODES.TAG, "Search " + object.toString());
//        String jsonInfo = object.toString();
//        JsonParser jsonParser = new JsonParser();

        try
        {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONArray item = jsonObject.getJSONArray("items");
            String titleInfo, linkInfo, descripsionInfo;

            for (int i = 0; i < item.length(); i++)
            {
                titleInfo = item.getJSONObject(i).getString("title");
                String title = titleInfo.replaceAll("<b>", "").replaceAll("</b>","");
                linkInfo = item.getJSONObject(i).getString("link");
                descripsionInfo = item.getJSONObject(i).getString("description");
                String description = descripsionInfo.replaceAll("<b>", "").replaceAll("</b>","");

                String naverFristJuso = "";
                String naverLastJuso = "";
                String daumJuso = "";

                searchTextView.append(title + "\n");
                searchTextView.append(description + "\n");

                if(linkInfo.contains("naver")){
                    int frist = linkInfo.indexOf("?");
                    int last = linkInfo.lastIndexOf("=");
                    naverFristJuso = linkInfo.substring(0,frist);
                    naverLastJuso = linkInfo.substring(last,linkInfo.length());
                    searchTextView.append(naverFristJuso +""+naverLastJuso + "\n");
                } else if(linkInfo.contains("daum")){
                    daumJuso = linkInfo.replaceAll("\\|","");
                    searchTextView.append(daumJuso + "\n");
                }

            }

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
