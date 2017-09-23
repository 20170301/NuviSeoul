package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;


public class PlanEditActivity extends CommonActivity {

    PlanEditDayFragment planEditDayActivity;
    PlanEditDateFragment planEditDateActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        TextView tv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        if(tv_title != null){
            tv_title.setText(getString(R.string.plan_make_title));
        }
        setSupportActionBar(toolbar);

        planEditDayActivity = new PlanEditDayFragment();
        planEditDateActivity = new PlanEditDateFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, planEditDayActivity);
        fragmentTransaction.commit();


    }

}
