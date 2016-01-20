package com.rapidsofttechnologies.myproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AND-18 on 11/26/2015.
 */
public class QLRecyclerViewAdapter extends RecyclerView.Adapter<QLRecyclerViewAdapter.DataViewHolder>{

    private ArrayList<DataOfRecyclerView> quesLevelData;
    private static QLRecyclerViewClickListener qlRecyclerViewClickListener;

    public QLRecyclerViewAdapter(ArrayList<DataOfRecyclerView> quesLevelData) {
        this.quesLevelData= quesLevelData;
    }

    @Override
    public QLRecyclerViewAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_ql_recycler_view, parent, false);

        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;

    }

    @Override
    public void onBindViewHolder(QLRecyclerViewAdapter.DataViewHolder holder, int position) {
        holder.recyclerTextView.setText(quesLevelData.get(position).getLevel());
        holder.recyclerTextView.setBackgroundColor(quesLevelData.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return quesLevelData.size();
    }

    public void setOnItemClickListener(QLRecyclerViewClickListener recyclerViewClickListener) {
        this.qlRecyclerViewClickListener = recyclerViewClickListener;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView recyclerTextView;
        public DataViewHolder(View itemView) {
            super(itemView);
            recyclerTextView = (TextView)itemView.findViewById(R.id.recycler_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            qlRecyclerViewClickListener.onItemClick(getAdapterPosition(),v);
        }
    }

    public interface QLRecyclerViewClickListener {
        public void onItemClick(int position, View v);
    }
}
