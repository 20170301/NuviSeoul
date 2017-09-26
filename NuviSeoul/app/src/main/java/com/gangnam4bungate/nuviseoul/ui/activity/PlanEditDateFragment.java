package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gangnam4bungate.nuviseoul.R;

/**
 * Created by Seo on 2017-07-23.
 */

public class PlanEditDateFragment extends Fragment {

    public PlanEditDateFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_plan_edit_date, container, false);

        Button button = (Button) v.findViewById(R.id.button2);
        if(button != null)
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), RecommendActivity.class));
                }
            });
        return v;
    }
}
