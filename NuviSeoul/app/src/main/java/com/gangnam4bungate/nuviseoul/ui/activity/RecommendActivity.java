package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wsseo on 2017. 7. 9..
 */

public class RecommendActivity extends CommonActivity {

    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<RecommendData> data;

    ImageView closehandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        closehandle = (ImageView) findViewById(R.id.close);

        closehandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = (SlidingDrawer)findViewById(R.id.slide);
                drawer.animateClose();
            }
        });

        data = fill_with_data();


        horizontalAdapter=new HorizontalAdapter(data, getApplication());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(RecommendActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);


    }

    public List<RecommendData> fill_with_data() {

        List<RecommendData> data = new ArrayList<>();

        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 1"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 2"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 3"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 4"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 5"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 6"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 7"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 8"));
        data.add(new RecommendData( R.mipmap.ic_launcher, "Image 9"));


        return data;
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {


        List<RecommendData> horizontalList = Collections.emptyList();
        Context context;


        public HorizontalAdapter(List<RecommendData> horizontalList, Context context) {
            this.horizontalList = horizontalList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.imageview);
                txtview=(TextView) view.findViewById(R.id.txtview);
            }
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_location, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.imageView.setImageResource(horizontalList.get(position).imageId);
            holder.txtview.setText(horizontalList.get(position).txt);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String list = horizontalList.get(position).txt.toString();
                    Toast.makeText(RecommendActivity.this, list, Toast.LENGTH_SHORT).show();
                }

            });

        }

        @Override
        public int getItemCount()
        {
            return horizontalList.size();
        }
    }
}
