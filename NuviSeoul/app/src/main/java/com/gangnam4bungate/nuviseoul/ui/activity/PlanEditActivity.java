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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.data.PlanData;
import com.gangnam4bungate.nuviseoul.data.PlanDetailData;
import com.gangnam4bungate.nuviseoul.database.DBOpenHelper;
import com.gangnam4bungate.nuviseoul.database.DataBases;
import com.gangnam4bungate.nuviseoul.map.Route;
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
    ArrayList<PlanDetailData> mPlanDetailInsertList = new ArrayList<PlanDetailData>();
    ArrayList<PlanDetailData> mPlanDetailUpdateList = new ArrayList<PlanDetailData>();
    ArrayList<Route> mLocationList = new ArrayList<Route>();
    boolean isPlanEdit = false;
    int mEditPlanId = 0;

    public static PlanEditActivity mPlanEditActivity = null;
    public static PlanEditActivity getInstance(){
        return mPlanEditActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);
        mPlanEditActivity = this;

        Intent intent = getIntent();
        if(intent != null && intent.getBooleanExtra("edit", false)){
            isPlanEdit = true;
            mEditPlanId = intent.getIntExtra("planid", 0);
        }
        mDBOpenHelper = DBOpenHelper.getInstance();
        if(mDBOpenHelper != null && mDBOpenHelper.isOpen() == false)
            mDBOpenHelper.open(getApplicationContext());
        initLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlanEditActivity = null;
    }

    public void setLocations(ArrayList<Route> locationList){
        mLocationList.clear();
        mLocationList.addAll(locationList);
    }

    public void initLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        TextView tv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        if(tv_title != null){
            if(isPlanEdit == false)
                tv_title.setText(getString(R.string.plan_make_title));
            else
                tv_title.setText(getString(R.string.edit_title));
        }
        setSupportActionBar(toolbar);

        Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);

        mPlanSubjectText = (EditText) findViewById(R.id.et_subject);
        mllAddLayout = (LinearLayout) findViewById(R.id.ll_addLayout);

        if(isPlanEdit == false) {
            if (mllAddLayout != null) {
                addLayout(mllAddLayout, null);
            }
        } else {
            Cursor c = mDBOpenHelper.plan_getColumn(mEditPlanId);
            if(c != null){
                if(c.getCount() > 0) {
                    try {
                        String name = c.getString(c.getColumnIndex(DataBases.CreatePlanDB._NAME));
                        if (name != null)
                            mPlanSubjectText.setText(name);
                    }catch(Exception e){

                    }
                }
                c.close();
            }

            Cursor cursor = mDBOpenHelper.plandetail_getColumn(mEditPlanId);
            if(cursor != null){
                do {
                    try {
                        PlanDetailData data = new PlanDetailData();

                        data.setId(cursor.getInt(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._ID)));
                        data.setPlanid(mEditPlanId);
                        String sdate = cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._STARTDATE));
                        String edate = cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._ENDDATE));

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date start_date = sdf.parse(sdate);
                        Date end_date = sdf.parse(edate);

                        data.setStartDate(start_date);
                        data.setEndDate(end_date);

                        data.setPathseq(cursor.getInt(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._PATH_SEQ)));
                        data.setPlacename(cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._PLACE_NAME)));
                        data.setLatitude(cursor.getDouble(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._PLACE_GPS_LATITUDE)));
                        data.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBases.CreatePlanDetailDB._PLACE_GPS_LONGITUDE)));
                        addLayout(mllAddLayout, data);
                    } catch (Exception e) {

                    }
                }while (cursor.moveToNext());
                cursor.close();
            }

            View addLayout = getLayoutInflater().inflate(R.layout.layout_plan_addlayout, null);
            if (addLayout != null) {
                mllAddLayout.addView(addLayout);
                addLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLayout(mllAddLayout, null);
                    }
                });
            }
        }

        mllAddDestination = (LinearLayout) findViewById(R.id.ll_adddestination);
        if(mllAddDestination != null){
            mllAddDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addLayout(mllAddLayout, null);
                }
            });
        }
        mSaveButton = (Button) findViewById(R.id.registerButton);
        if(mSaveButton != null){
            if(isPlanEdit == false){
                mSaveButton.setText(getString(R.string.save_title));
            } else {
                mSaveButton.setText(getString(R.string.edit_btn));
            }
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isPlanEdit == false) {
                        mPlanData.setName(mPlanSubjectText.getText().toString());
                        Date sDate = null;
                        Date eDate = null;

                        for (int i = 0; i < mPlanDetailInsertList.size(); i++) {
                            PlanDetailData data = mPlanDetailInsertList.get(i);
                            if (data != null) {
                                if(sDate == null)
                                    sDate = data.getStartDate();
                                else if(sDate.compareTo(data.getStartDate()) > 0){
                                    sDate = data.getStartDate();
                                }

                                if(eDate == null)
                                    eDate = data.getEndDate();
                                else if(eDate.compareTo(data.getEndDate()) < 0){
                                    eDate = data.getEndDate();
                                }
                            }
                        }

                        if(sDate != null)
                            mPlanData.setStart_date(sDate);
                        if(eDate != null)
                            mPlanData.setEnd_date(eDate);
                        mPlanData.setDetailDataList(mPlanDetailInsertList);

                        mDBOpenHelper.planInsert(mPlanData);
                        Cursor c = mDBOpenHelper.plan_getMatchName(mPlanData.getName());
                        int planid = 0;
                        if (c != null) {
                            if(c.moveToFirst())
                                planid = c.getInt(c.getColumnIndex(DataBases.CreatePlanDB._ID));
                            c.close();
                        }
                        for (int i = 0; i < mPlanDetailInsertList.size(); i++) {
                            PlanDetailData data = mPlanDetailInsertList.get(i);
                            if (data != null) {
                                data.setPlanid(planid);
                                mDBOpenHelper.plandetailInsert(data);
                            }
                        }
                    } else {
                        mPlanData.setName(mPlanSubjectText.getText().toString());
                        Date sDate = null;
                        Date eDate = null;

                        for (int i = 0; i < mPlanDetailInsertList.size(); i++) {
                            PlanDetailData data = mPlanDetailInsertList.get(i);
                            if (data != null) {
                                if(sDate == null)
                                    sDate = data.getStartDate();
                                else if(sDate.compareTo(data.getStartDate()) > 0){
                                    sDate = data.getStartDate();
                                }

                                if(eDate == null)
                                    eDate = data.getEndDate();
                                else if(eDate.compareTo(data.getEndDate()) < 0){
                                    eDate = data.getEndDate();
                                }
                            }
                        }

                        if(sDate != null)
                            mPlanData.setStart_date(sDate);
                        if(eDate != null)
                            mPlanData.setEnd_date(eDate);

                        ArrayList<PlanDetailData> all_list = new ArrayList<PlanDetailData>();
                        if(mPlanDetailInsertList.size() > 0)
                             all_list.addAll(mPlanDetailInsertList);
                        if(mPlanDetailUpdateList.size() > 0)
                             all_list.addAll(mPlanDetailUpdateList);
                        mPlanData.setDetailDataList(all_list);
                        mPlanData.setId(mEditPlanId);

                        mDBOpenHelper.planUpdate(mPlanData);

                        for (int i = 0; i < mPlanDetailInsertList.size(); i++) {
                            PlanDetailData data = mPlanDetailInsertList.get(i);
                            if (data != null) {
                                data.setPlanid(mEditPlanId);
                                mDBOpenHelper.plandetailInsert(data);
                            }
                        }

                        for (int i = 0; i < mPlanDetailUpdateList.size(); i++) {
                            PlanDetailData data = mPlanDetailUpdateList.get(i);
                            if (data != null) {
                                data.setPlanid(mEditPlanId);
                                mDBOpenHelper.plandetailUpdate(data);
                            }
                        }
                    }

                    finish();

                }
            });
        }
    }

    public void addLayout(final LinearLayout addView, PlanDetailData editdata){
        try {
            LinearLayout linearLayout = (LinearLayout) addView.findViewById(R.id.ll_addlayout);
            if (linearLayout != null) {
                addView.removeView(linearLayout);
            }

            PlanDetailData data;
            if (editdata == null) {
                data = new PlanDetailData();
            } else {
                data = editdata;
            }

            if(data != null) {
                if(data.getPathseq() == 0 || data.getPathseq() == -1){
                    final View view = getLayoutInflater().inflate(R.layout.layout_plan_edit_detail, null);
                    if (view != null)
                        addView.addView(view);
                    if(linearLayout != null)
                        addView.addView(linearLayout);

                    TextView tvStartDate = (TextView) view.findViewById(R.id.tv_startDate);
                    if (tvStartDate != null) {
                        tvStartDate.setTag(data);
                        if (editdata != null) {
                            Date sdate = editdata.getStartDate();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdate);
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH) + 1;
                            int day = cal.get(Calendar.DAY_OF_MONTH);

                            tvStartDate.setText(String.format("%d/%d/%d", year, month, day));
                        }
                        tvStartDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                final DatePickerDialog dateDialog = new DatePickerDialog(PlanEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        try {
                                            TextView textView = (TextView) v;
                                            if (textView != null) {
                                                textView.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                                Date date = dateFormat.parse(String.format("%d-%d-%d", year, month, dayOfMonth));
                                                PlanDetailData data = (PlanDetailData) textView.getTag();
                                                data.setStartDate(date);
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                                }, year, month, date);
                                dateDialog.show();
                            }
                        });
                    }

                    TextView tvEndDate = (TextView) view.findViewById(R.id.tv_endDate);
                    if (tvEndDate != null) {
                        tvEndDate.setTag(data);
                        if (editdata != null) {
                            Date date = editdata.getEndDate();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH) + 1;
                            int day = cal.get(Calendar.DAY_OF_MONTH);

                            tvEndDate.setText(String.format("%d/%d/%d", year, month, day));
                        }
                        tvEndDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                DatePickerDialog dateDialog = new DatePickerDialog(PlanEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        try {
                                            TextView textView = (TextView) v;
                                            if (textView != null) {
                                                textView.setText(String.format("%d/%d/%d", year, month, dayOfMonth));
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                                Date date = dateFormat.parse(String.format("%d-%d-%d", year, month, dayOfMonth));
                                                PlanDetailData data = (PlanDetailData) textView.getTag();
                                                data.setEndDate(date);
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                                }, year, month, date);
                                dateDialog.show();
                            }
                        });
                    }

                    final TextView tvAddPlace = (TextView) view.findViewById(R.id.tv_addplace);
                    if (tvAddPlace != null) {
                        tvAddPlace.setTag(data);
                        if (editdata != null) {
                            tvAddPlace.setText(editdata.getPlacename());
                        }
                        tvAddPlace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.setTag(v.getTag());
                                startActivityForResult(new Intent(getApplicationContext(), RecommendActivity.class), CODES.ActivityResult.LOCATIONS);
                            }
                        });
                    }

                    Button btClose = (Button) view.findViewById(R.id.bt_close);
                    if (btClose != null)
                        btClose.setTag(data);
                    btClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addView.removeView(view);
                            PlanDetailData data = (PlanDetailData) v.getTag();

                            for (int index = 0; index < mPlanDetailInsertList.size(); index++) {
                                PlanDetailData idata = mPlanDetailInsertList.get(index);
                                if (idata != null && idata.getPlacename().equals(data.getPlacename())
                                        && idata.getStartDate().equals(data.getStartDate())
                                        && idata.getEndDate().equals(data.getEndDate())) {

                                    mPlanDetailInsertList.remove(data);
                                    break;
                                }
                            }

                            for (int index = 0; index < mPlanDetailUpdateList.size(); index++) {
                                PlanDetailData idata = mPlanDetailUpdateList.get(index);
                                if (idata != null && idata.getPlacename().equals(data.getPlacename())
                                        && idata.getStartDate().equals(data.getStartDate())
                                        && idata.getEndDate().equals(data.getEndDate())) {

                                    mPlanDetailUpdateList.remove(data);
                                    break;
                                }
                            }
                        }
                    });
                } else if(data.getPathseq() >= 1){
                    updateLocationLayout(addView, data);
                }


            }
        }catch(Exception e){

        }
    }


    public void updateLocationLayout(LinearLayout parentView, PlanDetailData data){
        TextView tv_dest;
        View view = getLayoutInflater().inflate(R.layout.layout_plan_destination, null);
        if (view != null) {
            tv_dest = (TextView) view.findViewById(R.id.tv_addplace);
            if(tv_dest != null)
                tv_dest.setText(data.getPlacename());
        }
        parentView.addView(view);
    }


    public void addLocationLayout(LinearLayout parentView){
        try {
            LinearLayout addView = null;
            PlanDetailData data = null;
            for (int index = 0; index < parentView.getChildCount(); index++) {
                View childview = parentView.getChildAt(index);
                if (childview != null && childview.getTag() != null) {
                    data = (PlanDetailData)childview.getTag();
                    if(data != null){
                        addView = (LinearLayout)childview;
                        break;
                    }
                }
            }

            if(mLocationList.size() > 0) {
                TextView tv_dest = (TextView) addView.findViewById(R.id.tv_addplace);
                if (tv_dest != null) {
                    Route route = mLocationList.get(0);
                    tv_dest.setText(route.startTitle);
                    data.setLatitude(route.startLocation.latitude);
                    data.setLongitude(route.startLocation.longitude);
                    data.setPlacename(tv_dest.getText().toString());
                    data.setPathseq(0);

                    if(isPlanEdit == false){
                        mPlanDetailInsertList.add(data);
                    } else {
                        mPlanDetailUpdateList.add(data);
                    }
                }
                if (addView != null) {
                    for (int index = 1; index < mLocationList.size(); index++) {
                        Route route = mLocationList.get(index);
                        View view = getLayoutInflater().inflate(R.layout.layout_plan_destination, null);
                        if (view != null) {
                            tv_dest = (TextView) view.findViewById(R.id.tv_addplace);
                        }
                        if (tv_dest != null) {

                            PlanDetailData copy = new PlanDetailData();
                            copy.setId(data.getId());
                            copy.setPathseq(index);
                            copy.setEndDate(data.getEndDate());
                            copy.setStartDate(data.getStartDate());
                            copy.setPlanid(data.getPlanid());

                            if (index >= mLocationList.size() - 1) {
                                tv_dest.setText(route.endTitle);
                                copy.setLatitude(route.endLocation.latitude);
                                copy.setLongitude(route.endLocation.longitude);
                            } else {
                                tv_dest.setText(route.startTitle);
                                copy.setLatitude(route.startLocation.latitude);
                                copy.setLongitude(route.startLocation.longitude);
                            }
                            copy.setPlacename(tv_dest.getText().toString());

                            if(isPlanEdit == false){
                                mPlanDetailInsertList.add(copy);
                            } else {
                                mPlanDetailUpdateList.add(copy);
                            }
                            addView.addView(view);
                        }
                    }

                    addView.setTag(null);
                }
            }
        }catch (Exception e){

        }

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
            case CODES.ActivityResult.LOCATIONS:
            {
                addLocationLayout(mllAddLayout);
            }
            break;
        }
    }
}
