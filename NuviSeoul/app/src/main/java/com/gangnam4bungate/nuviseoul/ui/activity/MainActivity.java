package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.data.PlanData;
import com.gangnam4bungate.nuviseoul.database.DBOpenHelper;
import com.gangnam4bungate.nuviseoul.database.DataBases;
import com.gangnam4bungate.nuviseoul.holder.PlanListAdapter;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends CommonActivity{

    private TextView mTv_title;
    private ImageView mIv_add;
    private ImageView mIv_search;
    private RecyclerView mRvPlanList;
    private PlanListAdapter mPlanListAdapter;
    DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBOpenHelper = DBOpenHelper.getInstance();
        if(mDBOpenHelper != null && mDBOpenHelper.isOpen() == false)
            mDBOpenHelper.open(getApplicationContext());
        initLayout();
    }

    public void initLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        mTv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        if(mTv_title != null)
            mTv_title.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        mIv_add = (ImageView) findViewById(R.id.iv_add);
        if(mIv_add != null){
            mIv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getApplicationContext(), PlanEditActivity.class), CODES.ActivityResult.PLAN_EDIT);
                }
            });
        }
        mIv_search = (ImageView) findViewById(R.id.iv_search);
        if(mIv_search != null){
            mIv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                }
            });
        }

        Cursor cursor = mDBOpenHelper.planAllColumns();
        ArrayList<PlanData> planList = new ArrayList<PlanData>();
        if(cursor != null){
            try {
                do {
                    PlanData data = new PlanData();

                    data.setId(cursor.getInt(cursor.getColumnIndex(DataBases.CreatePlanDB._ID)));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date start_date = sdf.parse(cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDB._STARTDATE)));
                    Date end_date = sdf.parse(cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDB._ENDDATE)));

                    data.setStart_date(start_date);
                    data.setEnd_date(end_date);
                    data.setName(cursor.getString(cursor.getColumnIndex(DataBases.CreatePlanDB._NAME)));

                    planList.add(data);
                } while (cursor.moveToNext());

                cursor.close();

            }catch(Exception e){

            }
        }

        mRvPlanList = (RecyclerView) findViewById(R.id.rv_planlist);
        mRvPlanList.setVisibility(View.VISIBLE);

        mPlanListAdapter = new PlanListAdapter(this);

        mRvPlanList.setLayoutManager(new LinearLayoutManager(this));
        mRvPlanList.setAdapter(mPlanListAdapter);

        mPlanListAdapter.bindData(planList);

    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            case CODES.ActivityResult.PLAN_EDIT:
            {
                initLayout();
            }
            break;
        }
    }
}
