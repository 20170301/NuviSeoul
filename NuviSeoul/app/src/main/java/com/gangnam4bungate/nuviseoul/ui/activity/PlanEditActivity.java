package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.data.PlanData;
import com.gangnam4bungate.nuviseoul.data.PlanDetailData;
import com.gangnam4bungate.nuviseoul.database.DBOpenHelper;
import com.gangnam4bungate.nuviseoul.database.DataBases;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PlanEditActivity extends CommonActivity {
    Button mSaveButton;
    LinearLayout mllAddLayout;
    LinearLayout mllAddDestination;
    DBOpenHelper mDBOpenHelper;
    EditText mPlanSubjectText;
    int year, month, date;
    PlanData mPlanData = new PlanData();
    Date mPlanStartDate;
    Date mPlanEndDate;
    ArrayList<PlanDetailData> mPlanDetailList = new ArrayList<PlanDetailData>();
    final int ACTIVITY_RESULT_LOCATIONS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);

        mDBOpenHelper = DBOpenHelper.getInstance();
        if(mDBOpenHelper != null)
            mDBOpenHelper.open(getApplicationContext());
        initLayout();
    }

    public void initLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        TextView tv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        if(tv_title != null){
            tv_title.setText(getString(R.string.plan_make_title));
        }
        setSupportActionBar(toolbar);


        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);

        mPlanSubjectText = (EditText) findViewById(R.id.et_subject);
        mllAddLayout = (LinearLayout) findViewById(R.id.ll_addLayout);
        if(mllAddLayout != null) {
            addLayout(mllAddLayout);
        }

        mllAddDestination = (LinearLayout) findViewById(R.id.ll_adddestination);
        if(mllAddDestination != null){
            mllAddDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addLayout(mllAddLayout);
                }
            });
        }
        mSaveButton = (Button) findViewById(R.id.registerButton);
        if(mSaveButton != null){
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlanData.setName(mPlanSubjectText.getText().toString());
                    mPlanData.setStart_date(mPlanStartDate);
                    mPlanData.setEnd_date(mPlanEndDate);
                    mPlanData.setDetailDataList(mPlanDetailList);

                    mDBOpenHelper.planInsert(mPlanData);
                    Cursor c = mDBOpenHelper.plan_getMatchName(mPlanData.getName());
                    int planid = 0;
                    if(c != null){
                        while(c.moveToNext()){
                            planid = c.getInt(c.getColumnIndex(DataBases.CreatePlanDB._ID));
                            break;
                        }
                        c.close();
                    }
                    for(int i = 0; i < mPlanDetailList.size(); i++){
                        PlanDetailData data = mPlanDetailList.get(i);
                        if(data != null) {
                            data.setPlanid(planid);
                            mDBOpenHelper.plandetailInsert(data);
                        }
                    }

                    finish();

                }
            });
        }
    }

    public void addLayout(final LinearLayout addView){
        LinearLayout linearLayout = (LinearLayout) addView.findViewById(R.id.ll_adddestination);
        if (linearLayout != null) {
            addView.removeView(linearLayout);
        }
        final View view = getLayoutInflater().inflate(R.layout.layout_plan_edit_detail, null);
        if (view != null)
            addView.addView(view);
        addView.addView(linearLayout);

        PlanDetailData data = new PlanDetailData();
        mPlanDetailList.add(data);

        TextView tvStartDate = (TextView) view.findViewById(R.id.tv_startDate);
        if(tvStartDate != null){
            tvStartDate.setTag(data);
            tvStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final DatePickerDialog dateDialog = new DatePickerDialog(PlanEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            try {
                                TextView textView = (TextView) v;
                                if (textView != null){
                                    textView.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                    Date date = dateFormat.parse(String.format("%d-%d-%d", year, month, dayOfMonth));
                                    PlanDetailData data = (PlanDetailData) textView.getTag();
                                    data.setStartDate(date);
                                    mPlanStartDate = date;
                                }
                            }catch(Exception e){

                            }
                        }
                    }, year, month, date);
                    dateDialog.show();
                }
            });
        }

        TextView tvEndDate = (TextView) view.findViewById(R.id.tv_endDate);
        if(tvEndDate != null){
            tvEndDate.setTag(data);
            tvEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DatePickerDialog dateDialog = new DatePickerDialog(PlanEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            try {
                                TextView textView = (TextView) v;
                                if (textView != null){
                                    textView.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                    Date date = dateFormat.parse(String.format("%d-%d-%d", year, month, dayOfMonth));
                                    PlanDetailData data = (PlanDetailData) textView.getTag();
                                    data.setEndDate(date);
                                    if(mPlanEndDate != null) {
                                        if (date.compareTo(mPlanEndDate) > 0)
                                             mPlanEndDate = date;
                                    } else {
                                        mPlanEndDate = date;
                                    }

                                }
                            }catch(Exception e){

                            }
                        }
                    }, year, month, date);
                    dateDialog.show();
                }
            });
        }

        final TextView tvAddPlace = (TextView) view.findViewById(R.id.tv_addplace);
        if(tvAddPlace != null){
            tvAddPlace.setTag(data);
            tvAddPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Location location = Util.findGeoPoint(getApplicationContext(), "서울시 용산구 이촌동 동작대교");
//                    PlanDetailData data = (PlanDetailData) v.getTag();
//                    data.setLatitude(location.getLatitude());
//                    data.setLongitude(location.getLongitude());
//                    data.setPlacename("서울시 용산구 이촌동 동작대교");
//                    tvAddPlace.setText("동작대교");

                     startActivityForResult(new Intent(getApplicationContext(), RecommendActivity.class), ACTIVITY_RESULT_LOCATIONS);
                }
            });
        }

        Button btClose = (Button) view.findViewById(R.id.bt_close);
        if(btClose != null)
            btClose.setTag(data);
            btClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addView.removeView(view);
                    PlanDetailData data = (PlanDetailData) v.getTag();
                    mPlanDetailList.remove(data);
                }
            });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACTIVITY_RESULT_LOCATIONS:
            {

            }
            break;
        }
    }
}
