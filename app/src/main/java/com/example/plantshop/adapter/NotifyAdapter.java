package com.example.plantshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO_Notify;
import com.example.plantshop.fragment.Fragment_Notify;
import com.example.plantshop.fragment.Fragment_Pay_Complete;
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
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Fragment fragment);
    }

    public NotifyAdapter(Context context, ArrayList<Notification> listNT, ArrayList<Product> listPurchased, OnItemClickListener onItemClickListener) {
        this.listNT = listNT;
        this.listPurchased = listPurchased;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_notify, null));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position) {

        DAO_Notify daoNotify = new DAO_Notify();
        if (position < listNT.size()) {
            notification = listNT.get(position);
            holder.tv_Date.setText(notification.getDateOder());
        }

        try {
            if (listNT.get(position).getDateOder().equals(listNT.get(position - 1).getDateOder())) {
                holder.tv_Date.setVisibility(View.GONE);
                holder.underView.setBackgroundColor(Color.WHITE);
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d("Error", "Error: " + e.getMessage());

        }


        if (MainActivity.getID > 0) {
            if (notification.isStatus()) {
                holder.tv_TB.setText("Đã đặt hàng");
            } else {
                holder.tv_TB.setText("Đã huỷ đơn hàng");
            }
        } else {
            for (Guest g : MainActivity.listGuest) {
                if (g.getIdGuest() == notification.getIdGuest()) {

                    if (notification.isStatus()) {
                        holder.tv_TB.setText(g.getFullName() + " đã đặt hàng");
                    } else {
                        holder.tv_TB.setText(g.getFullName() + " huỷ đơn hàng");
                    }
                    break;
                }
            }
        }

        if (notification.isStatus()) {
            holder.tv_TB.setTextColor(Color.GREEN);
        } else {
            holder.tv_TB.setTextColor(Color.RED);
        }

        int curentStatus = notification.getCurrentStatus();

        if(curentStatus == 0){
            holder.tv_TB_Status.setText("Đang đợi xử lý");
            holder.tv_TB_Status.setTextColor(Color.YELLOW);
        } else if (curentStatus == 1) {
            holder.tv_TB_Status.setText("Đang giao hàng");
            holder.tv_TB_Status.setTextColor(Color.BLUE);
        }else if (curentStatus == 2) {
            holder.tv_TB_Status.setText("Đã giao");
            holder.tv_TB_Status.setTextColor(Color.GREEN);
        }else if (curentStatus == 3) {
            holder.tv_TB_Status.setText("Đã huỷ");
            holder.tv_TB_Status.setTextColor(Color.RED);
        }

        Product product = listPurchased.get(position);
        Picasso.get().load(product.getUrl_Img()).into(holder.img_TB_Product);
        holder.tv_TB_PlantName.setText(product.getTenSanPham());
        holder.tv_TB_PlantType.setText(product.getTheLoaiSanPham());
        holder.tv_TB_SoLuongMua.setText(product.getSoLuongMua() + " sản phẩm");

        holder.itemView.setOnClickListener(v -> {

            if (MainActivity.getID > 0) {
                if (onItemClickListener != null) {

                    Fragment fragment = new Fragment_Pay_Complete();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("check_COD", notification.isCheck_COD());
                    bundle.putBoolean("check_GHN", notification.isCheck_GHN());
                    bundle.putBoolean("check_Pay", notification.isCheck_Pay());
                    bundle.putDouble("sumPrice", notification.getSumPrice());

                    fragment.setArguments(bundle);
                    onItemClickListener.onItemClick(fragment);
                }
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cập nhật trạng thái");
                String[] status = {"Đang giao hàng", "Đã giao", "Đã huỷ"};


                builder.setItems(status, (dialog, which) -> {
                   int cStatus;
                    if(status[which].equals("Đang giao hàng")){
                        cStatus = 1;
                    } else if (status[which].equals("Đã giao")) {
                        cStatus = 2;
                    }else {
                        cStatus = 3;
                    }
                    notification.setCurrentStatus(cStatus);
                    daoNotify.updateNT(notification.getIdGuest(), notification);

                    listNT.set(position, notification);

                    // Thông báo cho Adapter rằng dữ liệu đã thay đổi
                    notifyItemChanged(position);

                    dialog.dismiss();
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }


        });
    }

    @Override
    public int getItemCount() {
        return listPurchased.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_Date, tv_TB, tv_TB_PlantName, tv_TB_PlantType, tv_TB_SoLuongMua, tv_TB_Status;
        public ImageView img_TB_Product;
        public LinearLayout ly_Date;
        public View underView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_TB = itemView.findViewById(R.id.tv_TB);
            tv_TB_PlantName = itemView.findViewById(R.id.tv_TB_PlantName);
            tv_TB_PlantType = itemView.findViewById(R.id.tv_TB_PlantType);
            tv_TB_SoLuongMua = itemView.findViewById(R.id.tv_TB_SoLuongMua);
            tv_TB_Status = itemView.findViewById(R.id.tv_TB_Status);
            img_TB_Product = itemView.findViewById(R.id.img_TB_Product);
            ly_Date = itemView.findViewById(R.id.ly_Date);
            underView = itemView.findViewById(R.id.underView);

        }
    }
}
