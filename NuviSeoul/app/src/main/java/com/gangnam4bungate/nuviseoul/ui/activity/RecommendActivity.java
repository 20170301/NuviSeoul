package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonGoogleMapActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wsseo on 2017. 7. 9..
 */

//public class RecommendActivity extends CommonActivity {
//public class RecommendActivity extends CommonGoogleMapActivity implements CommonActivity {
public class RecommendActivity extends CommonGoogleMapActivity {

    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<RecommendData> data;

    ImageView closehandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        this.mType=1;//수정모드

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        TextView tv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        if(tv_title != null){
            tv_title.setText(getString(R.string.plan_make_title));
        }
        setSupportActionBar(toolbar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        Paint paint = new Paint();

        paint.setAlpha(50);
        ((LinearLayout)findViewById(R.id.content)).setBackgroundColor(paint.getColor());
    }


    public List<RecommendData> fill_with_data() {

        List<RecommendData> data = new ArrayList<>();

        data.add(new RecommendData( R.mipmap.ic_launcher, "서울타워",37.5511694,126.98822659999996));
        data.add(new RecommendData( R.mipmap.ic_launcher, "경복궁",37.579617,126.97704099999999));
        data.add(new RecommendData( R.mipmap.ic_launcher, "63빌딩",37.5193776,126.94021029999999));


//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("location", );
//        Intent intent  = new Intent();
//        intent.putExtra("locations", new Bundle());
//        setResult(0, intent);

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

            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.imageview);

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

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String list = horizontalList.get(position).text.toString();
                    double lati = horizontalList.get(position).latitude;
                    double longi = horizontalList.get(position).longitude;

                    LatLng location = new LatLng(lati, longi);
                    /*

                    MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.recommend_location));

                    marker .position(new LatLng(lati, longi))
                            .title(list)
                            .snippet("Seoul");

                    MapMarkerDisplay(marker);
                    MapMarkerZoom(location);
                    MapLineDrawing(location);
                    MapPreviousLocation(location);
                    */
                    //맵에 마크 추가하기
                    MapMarkerDisplay(location);
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
