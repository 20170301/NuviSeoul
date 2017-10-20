package com.gangnam4bungate.nuviseoul.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.activity.PlanEditActivity;

/**
 * Created by wsseo on 2017. 8. 6..
 */

public class Holder {
    public static int TYPE_PLAN_LIST = 1000;
    public static RecyclerView.ViewHolder getHolder(Context context, int viewtype){
        if(viewtype == TYPE_PLAN_LIST){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = (View) inflater.inflate(R.layout.layout_planlist, null);
            return new PlanViewHolder(v);
        } else {
            return null;
        }
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public PlanViewHolder(View itemView) {
            super(itemView);

            if(itemView != null){
                textView = (TextView) itemView.findViewById(R.id.tv_date);
            }
        }

        public void setDate(String date){
            if(textView != null){
                textView.setText(date);
            }
        }

    }
}
