package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.config.searchDTO;
import com.gangnam4bungate.nuviseoul.config.searchDetailDTO;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;
import com.mystory.commonlibrary.network.MashupCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.gangnam4bungate.nuviseoul.config.CODES.RequestCode.REQUEST_AREABASEDETAILLIST;
import static com.gangnam4bungate.nuviseoul.config.CODES.RequestCode.REQUEST_AREABASELIST;

/**
 * Created by wsseo on 2017. 7. 9..
 */

public class SearchActivity extends CommonActivity implements MashupCallback {
    private ArrayList<searchDTO> searchDTOs = new ArrayList<>();
    private ArrayList<searchDetailDTO> searchDetailDTOs = new ArrayList<>();
    Bitmap firstImage_bit = null;
    back task;

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
        if(requestCode == REQUEST_AREABASEDETAILLIST){
            Log.d(CODES.TAG, "Search " + object.toString());
            try{
                JSONObject jsonObject = new JSONObject(object.toString());
                JSONObject response = jsonObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject locations = body.getJSONObject("items");
                JSONObject item = locations.getJSONObject("item");

                String homePage = "";
                String overView = "";

                overView = item.getString("overview");
                homePage = item.optString("homepage", "주소가 없어요.");
                if(homePage.contains("href")){
                    int first_index = homePage.indexOf("\"") + 1;
                    int last_index = homePage.lastIndexOf("\"") -1 ;
                    homePage = homePage.substring(first_index, last_index);
                }

                searchDetailDTOs.add(new searchDetailDTO(overView, homePage));

                if(searchDTOs.size() == searchDetailDTOs.size()){
                    RecyclerView view = (RecyclerView) findViewById(R.id.main_recyclerview);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                    Log.d("searchDetailSize", String.valueOf(searchDetailDTOs.size()));
                    Log.d("searchSize", String.valueOf(searchDTOs.size()));
                    searchRecyclerView searchRecyclerView = new searchRecyclerView(searchDTOs, searchDetailDTOs, this);
                    view.setLayoutManager(layoutManager);
                    view.setAdapter(searchRecyclerView);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }

        }else if(requestCode == REQUEST_AREABASELIST){
            Log.d(CODES.TAG, "Search " + object.toString());
//        String jsonInfo = object.toString();
//        JsonParser jsonParser = new JsonParser();

            try
            {
                JSONObject jsonObject = new JSONObject(object.toString());
                JSONObject response = jsonObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject locations = body.getJSONObject("items");
                JSONArray item = locations.getJSONArray("item");
                //

                String contentId = "";
                String title = "";
                String firstImage = "";
                String mapX = "";
                String mapY = "";
                for (int i =0; i < item.length(); i++){
                    contentId = item.getJSONObject(i).getString("contentid");
                    title = item.getJSONObject(i).getString("title");
                    mapX = item.getJSONObject(i).getString("mapx");
                    mapY = item.getJSONObject(i).getString("mapy");
                    firstImage = item.getJSONObject(i).optString("firstimage", "no Parsing");

                    searchDTOs.add(new searchDTO(contentId, title, firstImage, mapX, mapY));

                }

                for(int i = 0; i < searchDTOs.size(); i++){
                    String content_Id = searchDTOs.get(i).contentId;
                    NetworkManager.getInstance().requestAreaBaseDetailListInfo(this, content_Id);
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class back extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                firstImage_bit = BitmapFactory.decodeStream(is);


            }catch(IOException e){
                e.printStackTrace();
            }
            return firstImage_bit;
        }

    }

}
