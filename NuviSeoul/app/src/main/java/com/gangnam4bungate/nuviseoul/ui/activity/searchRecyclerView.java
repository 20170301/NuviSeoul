package com.gangnam4bungate.nuviseoul.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.searchDTO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by shj89 on 2017-09-16.
 */

public class searchRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<searchDTO> searchDTOs = new ArrayList<>();
    public searchRecyclerView(JSONArray jsonArray) {
        Log.d("jsonArray", String.valueOf(jsonArray));
        try {
            String titleInfo, linkInfo, descripsionInfo;
            for (int i =0; i < jsonArray.length(); i++){
                titleInfo = jsonArray.getJSONObject(i).getString("title");
                String title = titleInfo.replaceAll("<b>", "").replaceAll("</b>","");
                linkInfo = jsonArray.getJSONObject(i).getString("link");
                descripsionInfo = jsonArray.getJSONObject(i).getString("description");
                String description = descripsionInfo.replaceAll("<b>", "").replaceAll("</b>","");

                String naverFristJuso = "";
                String naverLastJuso = "";
                String daumJuso = "";

                String link = "";
                if(linkInfo.contains("naver")){
                    int frist = linkInfo.indexOf("?");
                    int last = linkInfo.lastIndexOf("=");
                    naverFristJuso = linkInfo.substring(0,frist);
                    naverLastJuso = linkInfo.substring(last,linkInfo.length());
                    link = naverFristJuso + "" + naverLastJuso;
                } else if(linkInfo.contains("daum")){
                    daumJuso = linkInfo.replaceAll("\\|","");
                    link = daumJuso;
                }

                searchDTOs.add(new searchDTO(title, description, link));
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RowCell)holder).searchTitle.setText(searchDTOs.get(position).title);
        ((RowCell)holder).searchTextView.setText(searchDTOs.get(position).description);
    }

    @Override
    public int getItemCount() {
        return searchDTOs.size();
    }


    private class RowCell extends RecyclerView.ViewHolder {
        EditText searchTitle;
        TextView searchTextView;

        public RowCell(View view) {
            super(view);
            searchTitle = (EditText) view.findViewById(R.id.searchTitle);
            searchTextView = (TextView) view.findViewById(R.id.searchTextView);
        }
    }
}
