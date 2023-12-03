package com.example.plantshop.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Cart extends Fragment {

    private ImageView img_Back, img_DeleteAll;
    private GridLayout gridlayout_Cart;
    private TextView tv_Cost;
    private Button btn_LetPay;

    private double sumCost = 0;
    private LinearLayout boardCost;
    private int size;
    public static ArrayList<Product> listPD_InCart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_DeleteAll = view.findViewById(R.id.img_DeleteAll);
        gridlayout_Cart = view.findViewById(R.id.gridlayout_Cart);
        tv_Cost = view.findViewById(R.id.tv_Cost);
        btn_LetPay = view.findViewById(R.id.btn_LetPay);
        boardCost = view.findViewById(R.id.boardCost);

        listPD_InCart = new ArrayList<>();

        img_Back.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
        });

        btn_LetPay.setOnClickListener(v -> {
            Fragment fragment = new Fragment_Choose_Pay();
            Bundle bundle = new Bundle();
            bundle.putDouble("price", sumCost);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });


        onView();


        return view;
    }



    public void onView() {

        sumCost = 0;

        List<View> listViewCart = new ArrayList<>();

        DatabaseReference databaseRef_getCart = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(MainActivity.getID) + "/");

        databaseRef_getCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {

                    String key = dataSnapshot.getRef().getKey(); // lấy tên loại
                    databaseRef_getCart.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Product product = dataSnapshot.getValue(Product.class);
                                listPD_InCart.add(product);

                                sumCost += product.getGiaTien();

                                View viewItemCart = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, gridlayout_Cart, false);

                                ImageView img_Cart_Product = viewItemCart.findViewById(R.id.img_Cart_Product);
                                ImageView img_Minus = viewItemCart.findViewById(R.id.img_Minus);
                                ImageView imgPlus = viewItemCart.findViewById(R.id.imgPlus);

                                TextView tv_Cart_ProductName = viewItemCart.findViewById(R.id.tv_Cart_ProductName);
                                TextView tv_Cart_Price = viewItemCart.findViewById(R.id.tv_Cart_Price);
                                TextView tv_Cart_Quantity = viewItemCart.findViewById(R.id.tv_Cart_Quantity);
                                TextView tv_Delete = viewItemCart.findViewById(R.id.tv_Delete);

                                Picasso.get().load(product.getUrl_Img()).into(img_Cart_Product);
                                tv_Cart_ProductName.setText(product.getTenSanPham());
                                tv_Cart_Price.setText(String.valueOf(product.getGiaTien()));
                                tv_Cart_Quantity.setText("1");
                                tv_Delete.setPaintFlags(tv_Delete.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


                                tv_Delete.setOnClickListener(v -> {

                                    Dialog bottomDialog = new Dialog(getContext());
                                    bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                                    Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                                    TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                                    TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);

                                    tv_Title.setText("Xác nhận xoá sản phẩm khỏi giỏ hàng?");

                                    btn_Accept.setOnClickListener(v1 -> {

                                        databaseRef_getCart.child(product.getLoaiSanPham()).child(String.valueOf(product.getIdSanPham())).removeValue();

                                        if (listPD_InCart.size() == 0) {
                                            gridlayout_Cart.removeAllViews();
                                            tv_Cost.setText("0");
                                        }
                                        onView();

                                        bottomDialog.dismiss();
                                    });

                                    tv_Cancel.setOnClickListener(v1 -> {
                                        bottomDialog.dismiss();
                                    });

                                    bottomDialog.show();
                                    bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                                    bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ

                                });

                                imgPlus.setOnClickListener(v -> { // Tăng số lượng

                                    int plus_Quantity = Integer.valueOf(tv_Cart_Quantity.getText().toString()) + 1;

                                    tv_Cart_Quantity.setText(String.valueOf(plus_Quantity));

                                    sumCost += product.getGiaTien();
                                    tv_Cost.setText(String.format("%.3f", sumCost / 1000));


                                    for (Product PD : listPD_InCart
                                         ) {
                                        if(PD.getTenSanPham().equals(product.getTenSanPham())) {
                                            PD.setSoLuongMua(plus_Quantity);
                                        }
                                    }
                                });

                                img_Minus.setOnClickListener(v -> { // gỉam số lượng

                                    int minus_Quantity = Integer.valueOf(tv_Cart_Quantity.getText().toString()) - 1;
                                    if (minus_Quantity <= 0) {
                                        minus_Quantity = 1;
                                    }

                                    sumCost -= product.getGiaTien();

                                    if (sumCost <= (product.getGiaTien() * size)) { // không cho giá tiền nhỏ hơn giá trị ban đầu
                                        sumCost = product.getGiaTien() * size;
                                    }

                                    for (Product PD : listPD_InCart
                                    ) {
                                        if(PD.getTenSanPham().equals(product.getTenSanPham())) {
                                            PD.setSoLuongMua(minus_Quantity);
                                        }
                                    }
                                    tv_Cart_Quantity.setText(String.valueOf(minus_Quantity));
                                    tv_Cost.setText(String.format("%.3f", sumCost / 1000));
                                });

                                tv_Cost.setText(String.format("%.3f", sumCost / 1000));


                                listViewCart.add(viewItemCart);


                                gridlayout_Cart.removeAllViews(); // làm mới layout và thiết lập lại số lượng hàng
                                size = listViewCart.size();
                                gridlayout_Cart.setRowCount(size);

                                for (View view : listViewCart  // đổ view lên layout
                                ) {
                                    gridlayout_Cart.addView(view);
                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        img_DeleteAll.setOnClickListener(v -> {

            Dialog bottomDialog = new Dialog(getContext());
            bottomDialog.setContentView(R.layout.layout_sheet_bottom);

            Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
            TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
            TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);

            tv_Title.setText("Xác nhận xoá tất cả đơn hàng?");

            btn_Accept.setOnClickListener(v1 -> {
                bottomDialog.dismiss();
                databaseRef_getCart.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        gridlayout_Cart.removeAllViews();
                        onView();
                        tv_Cost.setText("0");

                    }
                });
            });

            tv_Cancel.setOnClickListener(v1 -> {
                bottomDialog.dismiss();
            });

            bottomDialog.show();
            bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
            bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ

        });
    }
}
