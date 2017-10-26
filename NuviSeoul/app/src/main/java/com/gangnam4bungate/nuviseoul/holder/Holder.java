package com.gangnam4bungate.nuviseoul.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.activity.PlanEditActivity;
import com.gangnam4bungate.nuviseoul.ui.activity.RecommendActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by wsseo on 2017. 8. 6..
 */

public class Holder {
    public static int TYPE_PLAN_LIST = 1000;
    public static int TYPE_PLAN_DETAIL = 2000;
    public static int TYPE_TOUR_COURSE_LIST = 3000;

    public static RecyclerView.ViewHolder getHolder(Context context, int viewtype){
        if(viewtype == TYPE_PLAN_LIST){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = (View) inflater.inflate(R.layout.layout_planlist, null);
            return new PlanListViewHolder(v);
        } else if(viewtype == TYPE_PLAN_DETAIL){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = (View) inflater.inflate(R.layout.layout_plandetail, null);
            return new PlanDetailViewHolder(v);
        } else if(viewtype == TYPE_TOUR_COURSE_LIST){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = (View) inflater.inflate(R.layout.layout_tour_course_list, null);
            return new TourCourseListHolder(v);
        } else {
            return null;
        }
    }

    public static class PlanListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvDate;
        public PlanListViewHolder(View itemView) {
            super(itemView);

            if(itemView != null){
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            }
        }

        public void setName(String name){
            if(mTvName != null){
                mTvName.setText(" " + name);
            }
        }

        public void setDate(String date){
            if(mTvDate != null){
                mTvDate.setText(" " + date);
            }
        }


    }

    public static class PlanDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_place;
        public PlanDetailViewHolder(View itemView) {
            super(itemView);

            if(itemView != null){
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            }
            if(itemView != null){
                tv_place = (TextView) itemView.findViewById(R.id.tv_place);
            }
        }

        public void setDate(String date){
            if(tv_date != null){
                tv_date.setText(date);
            }
        }

        public void setPlace(String place){
            if(tv_place != null){
                tv_place.setText(place);
            }
        }


    }


    public static class TourCourseListHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ImageView mIvBg;
        public TourCourseListHolder(View itemView) {
            super(itemView);

            if(itemView != null){
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mIvBg = (ImageView) itemView.findViewById(R.id.iv_bg);
            }
        }

        public void setName(String name){
            if(mTvName != null){
                mTvName.setText(name);
            }
        }

        public void setBackground(Context context, String url){
            if(mIvBg != null){
                Picasso.with(context)
                        .load(url)
                        .into(mIvBg);
            }
        }
    }


}
