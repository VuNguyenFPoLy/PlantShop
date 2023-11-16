package com.example.plantshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.model.HistorySearch;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HistorySearch> listH;

    public HistoryAdapter(ArrayList<HistorySearch> listH, Context context) {
        this.listH = listH;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

        HistorySearch historySearch = listH.get(position);

        holder.tv_historyName.setText(historySearch.getNameSearch());
    }

    @Override
    public int getItemCount() {
        return listH.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_historyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_historyName = itemView.findViewById(R.id.tv_historyName);
        }
    }
}
