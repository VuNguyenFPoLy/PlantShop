package com.example.plantshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Guest;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private ArrayList<Notification> listNT;
    private ArrayList<Product> listPurchased;
    private Context context;
    private Notification notification;

    public NotifyAdapter(Context context, ArrayList<Notification> listNT, ArrayList<Product> listPurchased) {
        this.listNT = listNT;
        this.listPurchased = listPurchased;
        this.context = context;

    }
    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_notify, null));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position) {

        if (position < listNT.size()) {
             notification = listNT.get(position);
        }

        holder.tv_Date.setText(notification.getDateOder());


        if(MainActivity.getID > 0){
            if(notification.isStatus()){
                holder.tv_TB.setText("Đã đặt hàng");
            } else {
                holder.tv_TB.setText("Đã huỷ đơn hàng");
            }
        } else {
            for (Guest g : MainActivity.listGuest) {
                if(g.getIdGuest() == notification.getIdGuest()){
                    holder.tv_TB.setText(g.getFullName() + " đã đặt hàng");
                    break;
                }
            }
        }

        if (position < listPurchased.size()) {

            if(position == 1){
                holder.ly_Date.setVisibility(View.GONE);
            }

            Product product = listPurchased.get(position);
            Picasso.get().load(product.getUrl_Img()).into(holder.img_TB_Product);
            holder.tv_TB_PlantName.setText(product.getTenSanPham());
            holder.tv_TB_PlantType.setText(product.getTheLoaiSanPham());
            holder.tv_TB_SoLuongMua.setText(product.getSoLuongMua() + " sản phẩm");
        }
    }

    @Override
    public int getItemCount() {
        return listPurchased.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_Date, tv_TB, tv_TB_PlantName, tv_TB_PlantType, tv_TB_SoLuongMua;
        public ImageView img_TB_Product;
        public LinearLayout ly_Date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_TB = itemView.findViewById(R.id.tv_TB);
            tv_TB_PlantName = itemView.findViewById(R.id.tv_TB_PlantName);
            tv_TB_PlantType = itemView.findViewById(R.id.tv_TB_PlantType);
            tv_TB_SoLuongMua = itemView.findViewById(R.id.tv_TB_SoLuongMua);
            img_TB_Product = itemView.findViewById(R.id.img_TB_Product);
            ly_Date = itemView.findViewById(R.id.ly_Date);

        }
    }
}
