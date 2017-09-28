package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.dayDTO;

import java.util.ArrayList;

/**
 * Created by Seo on 2017-07-23.
 */

public class PlanEditDateFragment extends Fragment {
    private RecyclerView recyclerView;
    private DayRecyclerView dayRecyclerView;
    private ArrayList list = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String startDate = getArguments().getString("startDate").replace("-","");
        String endDate = getArguments().getString("endDate").replace("-","");
        int startDate_int = Integer.parseInt(startDate);
        int endDate_int = Integer.parseInt(endDate);

        int betweenDay = endDate_int - startDate_int;

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_day_recyclerview, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.dayRecyclerView);

        for(int i = 0; i <= betweenDay; i++){
            list.add(startDate_int + i);
        }

        dayRecyclerView = new DayRecyclerView(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dayRecyclerView);

//        Button button = (Button) v.findViewById(R.id.button2);
//        if(button != null)
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.getContext().startActivity(new Intent(v.getContext(), RecommendActivity.class));
//                }
//            });
        return v;
    }
}
