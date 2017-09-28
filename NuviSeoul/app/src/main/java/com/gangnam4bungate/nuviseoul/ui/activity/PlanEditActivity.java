package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;


public class PlanEditActivity extends CommonActivity {

    PlanEditDayFragment planEditDayActivity;
    PlanEditDateFragment planEditDateActivity;
    DayRecyclerView dayRecyclerView;
    EditText travelSubjectText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        travelSubjectText = (EditText) findViewById(R.id.travelSubjectText);

        planEditDayActivity = new PlanEditDayFragment();
        planEditDateActivity = new PlanEditDateFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, planEditDayActivity);
        fragmentTransaction.commit();


    }

    public String pleaseGetText() {
        return String.valueOf(travelSubjectText.getText());
    }


}
