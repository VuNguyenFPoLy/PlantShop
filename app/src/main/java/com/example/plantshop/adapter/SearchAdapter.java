package com.example.plantshop.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.firebase.DAO_History;
import com.example.plantshop.fragment.Fragment_Cart;
import com.example.plantshop.fragment.Fragment_Edit_Or_Delete;
import com.example.plantshop.fragment.Fragment_Search;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> listAllProduct;
    private OnItemClickListener onItemClickListener;
    private DAO_History daoH;


    public interface OnItemClickListener{
        void onItemClick(Fragment fragment);
    }

    public SearchAdapter(Context context, ArrayList<Product> listAllProduct, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listAllProduct = listAllProduct;
        this.onItemClickListener = onItemClickListener;

    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

        Product product = listAllProduct.get(position);

        Picasso.get().load(product.getUrl_Img()).into(holder.img_Search_Product);
        holder.tv_Search_PlantName.setText(product.getTenSanPham());
        holder.tv_Search_PlantType.setText(product.getTheLoaiSanPham());
        holder.tv_Search_PlantPrice.setText(String.valueOf(product.getGiaTien()));

        daoH = new DAO_History();

        holder.itemView.setOnClickListener(v -> {
            if(onItemClickListener != null){

                Fragment fragment;


                if(MainActivity.getID > 0){
                    fragment = new Fragment_Cart();
                    onItemClickListener.onItemClick(fragment);

                    daoH.saveHistory(product.getTenSanPham(), MainActivity.getID);
                }else {

                    fragment = new Fragment_Edit_Or_Delete();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("id", product.getIdSanPham());
                    bundle1.putString("from", "search");
                    bundle1.putString("type", product.getLoaiSanPham());
                    fragment.setArguments(bundle1);

                    onItemClickListener.onItemClick(fragment);
                }
            }

        });

    }


    @Override
    public int getItemCount() {
        return listAllProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_Search_Product;
        public TextView tv_Search_PlantName, tv_Search_PlantType, tv_Search_PlantPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Search_Product = itemView.findViewById(R.id.img_Search_Product);
            tv_Search_PlantName = itemView.findViewById(R.id.tv_Search_PlantName);
            tv_Search_PlantType = itemView.findViewById(R.id.tv_Search_PlantType);
            tv_Search_PlantPrice = itemView.findViewById(R.id.tv_Search_PlantPrice);

        }
    }
}
