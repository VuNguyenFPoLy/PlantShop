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
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> listAllProduct;
    private String searchName;

    public void filterProducts(String string) {
        searchName = string;
        notifyDataSetChanged();
    }

    public SearchAdapter (Context context, ArrayList<Product> listAllProduct){
        this.context = context;
        this.listAllProduct = listAllProduct;
        this.searchName = searchName;
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
