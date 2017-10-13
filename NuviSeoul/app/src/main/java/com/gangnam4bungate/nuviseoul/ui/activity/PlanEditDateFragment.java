package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gangnam4bungate.nuviseoul.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        String start_Date = getArguments().getString("startDate");
        String end_Date = getArguments().getString("endDate");
        int startDate_int = Integer.parseInt(start_Date);
        int endDate_int = Integer.parseInt(end_Date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long diff;
        long diffDays = 0;
        Date beginDate = null;
        Date endDate;

        try {
            beginDate = formatter.parse(start_Date);
            endDate = formatter.parse(end_Date);

            diff = endDate.getTime() - beginDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        int betweenDay = (int)diffDays;


        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_day_recyclerview, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.dayRecyclerView);

        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        for(int i = 0; i <= betweenDay; i++){
            cal.add(Calendar.DATE, 1);
            list.add(cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE));
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
