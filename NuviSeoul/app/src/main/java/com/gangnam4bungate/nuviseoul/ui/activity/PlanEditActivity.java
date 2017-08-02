package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;


public class PlanEditActivity extends CommonActivity {

    EditText travelSubjectText;
    PlanEditDayActivity planEditDayActivity;
    PlanEditDateActivity planEditDateActivity;

//    int year, month, date;
//    DatePickerDialog.OnDateSetListener mDateSetListener;
//
//    Button startDateButton;
//    Button endDateButton;
//    EditText startDateText;
//    EditText endDateText;
//    Button registerButton;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);
//        setContentView(R.layout.activity_plan_edit_day);


        planEditDayActivity = new PlanEditDayActivity();
        planEditDateActivity = new PlanEditDateActivity();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, planEditDayActivity);
        fragmentTransaction.commit();
//        View view = getLayoutInflater().inflate(R.layout.activity_plan_edit_day, null, false);
//
//
//        startDateButton = (Button) view.findViewById(R.id.startDateButton);
//        endDateButton = (Button) view.findViewById(R.id.endDateButton);
//        startDateText = (EditText) view.findViewById(R.id.startDateText);
//        endDateText = (EditText) view.findViewById(R.id.endDateText);
//        registerButton = (Button) view.findViewById(R.id.registerButton);


//        Calendar cal = Calendar.getInstance();
//        year = cal.get(cal.YEAR);
//        month = cal.get(cal.MONTH) + 1;
//        date = cal.get(cal.DATE);
//
//        startDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startDateText.setText("aa");
//                new DatePickerDialog(PlanEditActivity.this, mDateSetListener, year, month, date).show();
//            }
//        });
//
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                startDateText.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
//            }
//        };

//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_place, new PlanEditDateActivity());
//                fragmentTransaction.commit();
//            }
//        });


    }

}
