package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gangnam4bungate.nuviseoul.R;

/**
 * Created by Seo on 2017-07-23.
 */

public class PlanEditDateActivity extends Fragment {

    public PlanEditDateActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_plan_edit_date, container, false);
    }
}
