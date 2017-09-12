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
        //Log.d(CODES.TAG, "Search " + object.toString());
//        String jsonInfo = object.toString();
//        JsonParser jsonParser = new JsonParser();

        try
        {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONArray item = jsonObject.getJSONArray("items");
            String titleInfo = "";
            String linkInfo = "";

            for (int i = 0; i < item.length(); i++)
            {
                titleInfo = item.getJSONObject(i).getString("title");
                linkInfo = item.getJSONObject(i).getString("link");

                searchTextView.append(titleInfo + "\n");
                searchTextView.append(linkInfo + "\n");
            }
//            searchTextView.setText(""+titleInfo+"\n"+""+linkInfo);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
