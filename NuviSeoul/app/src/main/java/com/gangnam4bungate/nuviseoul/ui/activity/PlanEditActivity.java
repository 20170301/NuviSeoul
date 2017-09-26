package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;


public class PlanEditActivity extends CommonActivity {

    PlanEditDayFragment planEditDayActivity;
    PlanEditDateFragment planEditDateActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);


        planEditDayActivity = new PlanEditDayFragment();
        planEditDateActivity = new PlanEditDateFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, planEditDayActivity);
        fragmentTransaction.commit();


    }

}
