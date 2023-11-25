package com.example.plantshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.model.Help;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Help> listHelp;

    public HelpAdapter(ArrayList<Help> listHelp, Context context) {
        this.listHelp = listHelp;
        this.context = context;
    }
    @NonNull
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_help, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        Help help = listHelp.get(position);
        holder.tv_Question.setText(help.getTv_Question());
        holder.tv_Content.setText(help.getTv_Reply());
        if(help.getImgShow() != null) {
            Picasso.get().load(String.valueOf(help.getImgShow())).into(holder.img_Show);
        }

    }

    @Override
    public int getItemCount() {
        return listHelp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Question;
        public TextView tv_Content;
        public ImageView img_Show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Question = itemView.findViewById(R.id.tv_Question);
            tv_Content = itemView.findViewById(R.id.tv_Content);
            img_Show = itemView.findViewById(R.id.img_Show);
        }
    }
}
