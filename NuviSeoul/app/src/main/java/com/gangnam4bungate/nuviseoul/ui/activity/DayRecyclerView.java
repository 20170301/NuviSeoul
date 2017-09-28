package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.dayDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shj89 on 2017-09-27.
 */

public class DayRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Integer> list = new ArrayList<>();
    private ArrayList<dayDTO> dayDTOs = new ArrayList<dayDTO>();


    public DayRecyclerView(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_plan_edit_date, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RowCell)holder).editText2.setText(Integer.toString(list.get(position)));
        ((RowCell)holder).button2.setText("+");

        ((RowCell)holder).button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), RecommendActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        EditText editText2;
        Button button2;

        public RowCell(View view) {
            super(view);
            editText2 = (EditText) view.findViewById(R.id.editText2);
            button2 = (Button) view.findViewById(R.id.button2);
        }
    }
}
