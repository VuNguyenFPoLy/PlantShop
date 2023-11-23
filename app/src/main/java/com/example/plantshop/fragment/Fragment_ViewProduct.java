package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_ViewProduct extends Fragment {

    private ImageView img_Back,  img_Product, img_Minus, img_add_Quantity, img_addToCart;
    private TextView tv_NameProduct, tv_Type_Product, tv_TypeOf_Product, tv_Price_Product
            , tv_Size_Product, tv_Brand_Product, tv_Quantity_Product, tv_Describe_Product,
            tv_Quantity, tv_CostView;

    private Button btn_Buy;
    private ArrayList<Product> listProduct;
    private String getFrom, type, key;
    private int id;
    private Double price;
    private Product product;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        img_Back= view.findViewById(R.id.img_Back);
        img_Product= view.findViewById(R.id.img_Product);
        img_Minus= view.findViewById(R.id.img_Minus);
        img_add_Quantity= view.findViewById(R.id.img_add_Quantity);
        img_addToCart= view.findViewById(R.id.img_addToCart);
        tv_NameProduct= view.findViewById(R.id.tv_NameProduct);
        tv_Type_Product= view.findViewById(R.id.tv_Type_Product);
        tv_TypeOf_Product= view.findViewById(R.id.tv_TypeOf_Product);
        tv_Price_Product= view.findViewById(R.id.tv_Price_Product);
        tv_Size_Product= view.findViewById(R.id.tv_Size_Product);
        tv_Brand_Product= view.findViewById(R.id.tv_Brand_Product);
        tv_Quantity_Product= view.findViewById(R.id.tv_Quantity_Product);
        tv_Describe_Product= view.findViewById(R.id.tv_Describe_Product);
        tv_Quantity= view.findViewById(R.id.tv_Quantity);
        tv_CostView= view.findViewById(R.id.tv_CostView);
        btn_Buy= view.findViewById(R.id.btn_Buy);

        Bundle getData = getArguments();

        id = getData.getInt("id");
        getFrom = getData.getString("from");
        type = getData.getString("type");

        key = getData.getString("key");

        if(getFrom.equals("product")){ // kiểm tra dữ liệu từ fragment nào chuyển đến
            listProduct = Fragment_Product.listProduct;
        }else {
            if(type.equals("Cây trồng")){
                listProduct = Fragment_Home.listPlant;
            } else if (type.equals("Chậu cây")) {
                listProduct = Fragment_Home.listPots;
            }else {
                listProduct = Fragment_Home.listTools;
            }
        }

        for (Product pd : listProduct
        ) {
            if (id == pd.getIdSanPham()) {

                Picasso.get().load(pd.getUrl_Img()).into(img_Product);
                tv_NameProduct.setText(pd.getTenSanPham());
                tv_Type_Product.setText(pd.getLoaiSanPham());
                tv_TypeOf_Product.setText(pd.getTheLoaiSanPham());
                tv_Price_Product.setText(String.format("%.3f",pd.getGiaTien()/1000) + " VNĐ");
                tv_Size_Product.setText(pd.getKichCo());
                tv_Brand_Product.setText(pd.getXuatXu());
                tv_Quantity_Product.setText("Còn " + pd.getSoLuong() + " sản phẩm");
                tv_Describe_Product.setText(pd.getMoTa());

                price = pd.getGiaTien();
                product = pd;
            }
        }

        img_Back.setOnClickListener(v -> {
            Fragment fragment;
            if(getFrom.equals("home")){
                MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
            } else if (getFrom.equals("product")) {
                 fragment = new Fragment_Product();
                 Bundle bundle = new Bundle();
                 bundle.putString("key", key);
                 fragment.setArguments(bundle);
                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
            } else if (getFrom.equals("search")) {
                MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Search);
            }
        });

        // Tăng giảm số lượng và tổng tiền

        tv_Quantity.setText("1");
        tv_CostView.setText(String.format("%.3f", price/1000) + "VNĐ");

        img_Minus.setOnClickListener(v -> { // Giảm

            int minus_Quantity = Integer.valueOf(tv_Quantity.getText().toString()) - 1;

            if(minus_Quantity <= 1){
                minus_Quantity = 1;
            }
            double sumCost = (price * minus_Quantity)/1000;

            tv_CostView.setText(String.format("%.3f",sumCost) + "VNĐ" );
            tv_Quantity.setText(String.valueOf(minus_Quantity));

        });

        img_add_Quantity.setOnClickListener(v -> { // Tăng

            int plus_Quantity = Integer.valueOf(tv_Quantity.getText().toString()) + 1;
            double sumCost = (price * plus_Quantity)/1000;

            tv_Quantity.setText(String.valueOf(plus_Quantity));
            tv_CostView.setText(String.format("%.3f",sumCost) + "VNĐ");

        });

        // Tiến hành mua và thêm vào giỏ hàng

        DatabaseReference databaseRef_addToCart = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(MainActivity.getID));


        img_addToCart.setOnClickListener(v -> {
            databaseRef_addToCart.child(product.getLoaiSanPham()).child(String.valueOf(product.getIdSanPham())).setValue(product);
            Toast.makeText(getContext(), "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        btn_Buy.setOnClickListener(v ->{
            databaseRef_addToCart.child(product.getLoaiSanPham()).child(String.valueOf(product.getIdSanPham())).setValue(product);
            Fragment fragment = new Fragment_Cart();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();

        });


        return view;
    }
}
