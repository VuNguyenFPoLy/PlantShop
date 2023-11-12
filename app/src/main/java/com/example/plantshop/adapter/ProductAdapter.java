package com.example.plantshop.adapter;

import android.content.Context;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> listProduct;

    public ProductAdapter(ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_product, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        Product product = listProduct.get(position);
        holder.tv_PlantName.setText(product.getTenSanPham());
        holder.tv_PlantType.setText(product.getLoaiSanPham());
        holder.tv_PlantPrice.setText(String.valueOf(product.getGiaTien()) + " VNĐ");
        if (product.getUrl_Img() != null) {
            Picasso.get().load(product.getUrl_Img()).into(holder.img_Item_Product);
        }

    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_Item_Product;
        public TextView tv_PlantName, tv_PlantType, tv_PlantPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Item_Product = itemView.findViewById(R.id.img_Item_Product);
            tv_PlantName = itemView.findViewById(R.id.tv_PlantName);
            tv_PlantType = itemView.findViewById(R.id.tv_PlantType);
            tv_PlantPrice = itemView.findViewById(R.id.tv_PlantPrice);
        }
    }

}
