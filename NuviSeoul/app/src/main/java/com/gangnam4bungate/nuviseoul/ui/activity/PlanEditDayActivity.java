package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gangnam4bungate.nuviseoul.R;

public class PlanEditDayActivity extends Fragment {
    Button startDateButton;
    Button endDateButton;
    EditText startDateText;
    EditText endDateText;
    Button registerButton;

        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_plan_edit_day, container, false);

            startDateButton = (Button) view.findViewById(R.id.startDateButton);
            endDateButton = (Button) view.findViewById(R.id.endDateButton);
            startDateText = (EditText) view.findViewById(R.id.startDateText);
            endDateText = (EditText) view.findViewById(R.id.endDateText);
            registerButton = (Button) view.findViewById(R.id.registerButton);

            startDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDateText.setText("aa");
                }
            });

        return view;
    }


}
