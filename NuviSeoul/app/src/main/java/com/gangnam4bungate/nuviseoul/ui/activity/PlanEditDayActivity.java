package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gangnam4bungate.nuviseoul.R;

import java.util.Calendar;

public class PlanEditDayActivity extends Fragment{
    Button startDateButton;
    Button endDateButton;
    EditText startDateText;
    EditText endDateText;
    Button registerButton;
    int year, month, date;
    DatePickerDialog.OnDateSetListener mDateSetListener;

        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            final View view = inflater.inflate(R.layout.activity_plan_edit_day, container, false);

            startDateButton = (Button) view.findViewById(R.id.startDateButton);
            endDateButton = (Button) view.findViewById(R.id.endDateButton);
            startDateText = (EditText) view.findViewById(R.id.startDateText);
            endDateText = (EditText) view.findViewById(R.id.endDateText);
            registerButton = (Button) view.findViewById(R.id.registerButton);

        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);

        startDateButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                startDateText.setText("aa");
            DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), startListener, year, month, date);
            dateDialog.show();

            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), endListener, year, month, date);
                dateDialog.show();
            }
        });


            registerButton.setOnClickListener(new View.OnClickListener() {
                PlanEditDateActivity planEditDateActivity = new PlanEditDateActivity();
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_place, planEditDateActivity);
                    fragmentTransaction.commit();
                }
            });

        return view;

    }

    private DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            startDateText.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
        }
    };

    private DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            endDateText.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
        }
    };



}
