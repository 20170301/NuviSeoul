package com.gangnam4bungate.nuviseoul.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.data.PlanDetailData;
import com.gangnam4bungate.nuviseoul.data.PlanDetailList;
import com.gangnam4bungate.nuviseoul.ui.activity.PlanEditActivity;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wsseo on 2017. 8. 6..
 */

public class PlanDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<PlanDetailList> mPlanList;
    private Activity mActivity;

    public PlanDetailAdapter(Activity activity){
        mActivity = activity;
    }

    public void bindData(ArrayList<PlanDetailList> list){
        mPlanList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return Holder.getHolder(parent.getContext(), viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == Holder.TYPE_PLAN_DETAIL){
            Holder.PlanDetailViewHolder planViewHolder = (Holder.PlanDetailViewHolder)holder;
            PlanDetailList detailList = mPlanList.get(position);

            if(detailList != null) {
                ArrayList<PlanDetailData> list = detailList.getArrayList();
                Date sDate = list.get(0).getStartDate();
                Date eDate = list.get(0).getEndDate();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start_date = sdf.format(sDate);
                String end_date = sdf.format(eDate);

                if (start_date != null && end_date != null) {
                    if (start_date.equals(end_date)) {
                        planViewHolder.setDate(start_date + " ");
                    } else {
                        planViewHolder.setDate(start_date + " ~ " + end_date);
                    }
                }

                planViewHolder.itemView.setTag(list.get(0));
                planViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlanDetailData data = (PlanDetailData) v.getTag();
                        if (data != null) {
                            Intent intent = new Intent(mActivity, PlanEditActivity.class);
                            intent.putExtra("edit", true);
                            intent.putExtra("planid", data.getPlanid());
                            mActivity.startActivityForResult(intent, CODES.ActivityResult.PLAN_EDIT);
                        }
                    }
                });
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        return Holder.TYPE_PLAN_DETAIL;
    }
}
