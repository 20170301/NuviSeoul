package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.config.searchDTO;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.mystory.commonlibrary.network.HttpClientManager;
import com.mystory.commonlibrary.network.MashupCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by shj89 on 2017-09-16.
 */

public class searchRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MashupCallback{

    private ArrayList<searchDTO> searchDTOs = new ArrayList<>();


    public searchRecyclerView(JSONArray jsonArray) {
        Log.d("jsonArray", String.valueOf(jsonArray));
        try {
            String contentId = "";
            String title = "";
            String firstImage = "";
            String mapX = "";
            String mapY = "";
            Bitmap homePage = null;
            String overView = "";
            for (int i =0; i < jsonArray.length(); i++){
                contentId = jsonArray.getJSONObject(i).getString("contentid");
                title = jsonArray.getJSONObject(i).getString("title");
                mapX = jsonArray.getJSONObject(i).getString("mapx");
                mapY = jsonArray.getJSONObject(i).getString("mapy");
                firstImage = jsonArray.getJSONObject(i).optString("firstimage");

                try {
                    URL url = new URL(firstImage);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    homePage = BitmapFactory.decodeStream(is);

                }catch (Exception e){

                }


//                if(jsonArray.getJSONObject(i).getString("firstimage") != null){
//                    firstImage = jsonArray.getJSONObject(i).getString("firstimage");
//                }else{
//                    firstImage = null;
//                }
//
//                NetworkManager.getInstance().requestAreaBaseDetailListInfo(this, contentId);

                searchDTOs.add(new searchDTO(contentId, title, firstImage, mapX, mapY, homePage, overView));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search,parent,false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((RowCell)holder).searchTitle.setText(searchDTOs.get(position).title);

        ((RowCell)holder).searchImage.setImageBitmap(searchDTOs.get(position).homePage);

//        ((RowCell)holder).searchTextView.setText(searchDTOs.get(position).description);



        ((RowCell) holder).searchTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                NetworkManager.getInstance().requestAreaBaseDetailListInfo(this, searchDTOs.get(position).contentId);

                Toast.makeText(v.getContext(),searchDTOs.get(position).title,Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchDTOs.size();
    }


    private class RowCell extends RecyclerView.ViewHolder {
        TextView searchTitle;
        TextView searchDescription;
        TextView searchHomepage;
        ImageView searchImage;

        public RowCell(View view) {
            super(view);
            searchTitle = (TextView) view.findViewById(R.id.searchTitle);
            searchDescription = (TextView) view.findViewById(R.id.searchDescription);
            searchHomepage = (TextView) view.findViewById(R.id.searchHomepage);
            searchImage = (ImageView) view.findViewById(R.id.searchImage);
        }
    }

    @Override
    public void onMashupSuccess(JSONObject object, String requestCode) {

    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {

    }

}


