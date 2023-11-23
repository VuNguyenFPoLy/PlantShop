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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> listPayment;

    public PaymentAdapter(Context context, ArrayList<Product> listPayment){
        this.context = context;
        this.listPayment = listPayment;

    }
    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {

        Product product = listPayment.get(position);

        Picasso.get().load(product.getUrl_Img()).into(holder.img_Pay_Product);
        holder.tv_Pay_ProductName.setText(product.getTenSanPham());
        holder.tv_Pay_ProductType.setText(product.getTheLoaiSanPham());
        holder.tv_Pay_Price.setText(String.valueOf(product.getGiaTien()));


        holder.tv_Pay_Quantity.setText(String.valueOf(product.getSoLuongMua()));

    }

    @Override
    public int getItemCount() {
        return listPayment.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_Pay_Product;
        public TextView tv_Pay_ProductName, tv_Pay_ProductType, tv_Pay_Price, tv_Pay_Quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Pay_Product = itemView.findViewById(R.id.img_Pay_Product);
            tv_Pay_ProductName = itemView.findViewById(R.id.tv_Pay_ProductName);
            tv_Pay_ProductType = itemView.findViewById(R.id.tv_Pay_ProductType);
            tv_Pay_Price = itemView.findViewById(R.id.tv_Pay_Price);
            tv_Pay_Quantity = itemView.findViewById(R.id.tv_Pay_Quantity);

        }
    }
}
