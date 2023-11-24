package com.example.plantshop.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.fragment.Fragment_View_Or_Add_HandBook;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HandBookAdapter extends RecyclerView.Adapter<HandBookAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> listPlant;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Fragment fragment);
    }

    public HandBookAdapter(Context context, ArrayList<Product> listPlant, OnItemClickListener listener) {
        this.context = context;
        this.listPlant = listPlant;
        this.listener = listener;
    }
    @NonNull
    @Override
    public HandBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_handbook, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HandBookAdapter.ViewHolder holder, int position) {

        Product product = listPlant.get(position);

        Picasso.get().load(product.getUrl_Img()).into(holder.img_CamNang);
        holder.tv_CN_NamePlant.setText(product.getTenSanPham());
        holder.tv_CN_PlantType.setText(product.getTheLoaiSanPham());

        holder.itemView.setOnClickListener(v -> {

            Fragment fragment = new Fragment_View_Or_Add_HandBook();
            Bundle bundle = new Bundle();
            bundle.putInt("idPlant", product.getIdSanPham());

            fragment.setArguments(bundle);
            listener.onItemClick(fragment);

        });

    }

    @Override
    public int getItemCount() {
        return listPlant.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_CamNang;
        public TextView tv_CN_NamePlant, tv_CN_PlantType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_CamNang = itemView.findViewById(R.id.img_CamNang);
            tv_CN_NamePlant = itemView.findViewById(R.id.tv_CN_NamePlant);
            tv_CN_PlantType = itemView.findViewById(R.id.tv_CN_PlantType);
        }
    }
}
