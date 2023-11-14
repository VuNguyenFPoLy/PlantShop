package com.example.plantshop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.ProductAdapter;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment_Product extends Fragment {

    private ImageView img_Back, ic_Cart;
    private TextView tv_Label, tv_All, tv_UaBong, tv_UaMat, tv_UaSang, tv_UaToi, tv_FAB;
    private RecyclerView rc_Product;
    private DAO_Product dao_product;
    private boolean checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi;
    private Query query;
    public static String key;
    public static ArrayList<Product> listProduct = new ArrayList<>();
    private GridLayout grid_ItemLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        ic_Cart = view.findViewById(R.id.ic_Cart);
        tv_Label = view.findViewById(R.id.tv_Label);
        grid_ItemLayout = view.findViewById(R.id.grid_ItemLayout);
        tv_FAB = view.findViewById(R.id.tv_FAB);
        rc_Product = view.findViewById(R.id.rc_Product);

        if (MainActivity.id <= 0) {
            ic_Cart.setVisibility(View.GONE);
        }

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product");

        Bundle bundle = getArguments();
        key = bundle.getString("key");

        List<View> item_View = new ArrayList<>();
        grid_ItemLayout.removeAllViews();

        if (key.equals("Xem thêm cây trồng")) {

            query = databaseRef.child("Cây trồng");
            tv_Label.setText("Cây trồng");

            List<String> itemType = Arrays.asList("Tất cả", "Ưa bóng", "Ưa mát", "Ưa sáng", "Ưa tối");

            grid_ItemLayout.setColumnCount(itemType.size());

            for (int i = 0; i < itemType.size(); i++) {
                View itemProductView = LayoutInflater.from(getContext()).inflate(R.layout.item_type_product, grid_ItemLayout, false);
                TextView textItem = itemProductView.findViewById(R.id.textItem);

                String string = itemType.get(i).toString();
                textItem.setText(string);

                item_View.add(itemProductView);

                if(string.equals("Tất cả")){ // mặc định màu cho type tất cả
                    textItem.setTextColor(getResources().getColor(R.color.white));
                    textItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                }

                textItem.setOnClickListener(v -> { // thay đổi màu chữ, màu nền, và câu lệnh truy vấn
                    for (int j = 0; j < itemType.size(); j++) {
                        TextView currentItem = item_View.get(j).findViewById(R.id.textItem);
                        if (itemType.get(j).toString().equals(string)) {
                            currentItem.setTextColor(getResources().getColor(R.color.white));
                            currentItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                            if(string.equals("Tất cả")){
                                query = databaseRef.child("Cây trồng");
                            }else {
                                query = databaseRef.child("Cây trồng").orderByChild("theLoaiSanPham").equalTo(string);

                            }
                            updateRecyclerView(query);

                        } else {
                            currentItem.setTextColor(getResources().getColor(R.color.color_Plant_Type));
                            currentItem.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    }
                });
            }

            for (View viewIT : item_View) { // đổ item lên view
                grid_ItemLayout.addView(viewIT);
            }

        } else if (key.equals("Xem thêm chậu cây")) {

            query = databaseRef.child("Chậu cây");
            tv_Label.setText("Chậu cây");

            List<String> itemType = Arrays.asList("Tất cả", "Gốm", "Xứ", "Xi măng", "Nhựa", "Đất nung");

            grid_ItemLayout.setColumnCount(itemType.size());

            for (int i = 0; i < itemType.size(); i++) {
                View itemProductView = LayoutInflater.from(getContext()).inflate(R.layout.item_type_product, grid_ItemLayout, false);
                TextView textItem = itemProductView.findViewById(R.id.textItem);

                String string = itemType.get(i).toString();
                textItem.setText(string);

                item_View.add(itemProductView);

                if(string.equals("Tất cả")){ // mặc định màu cho type tất cả
                    textItem.setTextColor(getResources().getColor(R.color.white));
                    textItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                }

                textItem.setOnClickListener(v -> { // thay đổi màu chữ, màu nền, và câu lệnh truy vấn
                    for (int j = 0; j < itemType.size(); j++) {
                        TextView currentItem = item_View.get(j).findViewById(R.id.textItem);
                        if (itemType.get(j).toString().equals(string)) {
                            currentItem.setTextColor(getResources().getColor(R.color.white));
                            currentItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                            if(string.equals("Tất cả")){
                                query = databaseRef.child("Chậu cây");
                            }else {
                                query = databaseRef.child("Chậu cây").orderByChild("theLoaiSanPham").equalTo(string);

                            }
                            updateRecyclerView(query);

                        } else {
                            currentItem.setTextColor(getResources().getColor(R.color.color_Plant_Type));
                            currentItem.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    }
                });
            }

            for (View viewIT : item_View) { // đổ item lên view
                grid_ItemLayout.addView(viewIT);
            }

        } else {
            query = databaseRef.child("Dụng cụ");
            tv_Label.setText("Dụng cụ");

            List<String> itemType = Arrays.asList("Tất cả", "Găng tay", "Cuốc", "Xẻng", "Cào đất", "Bay", "Cưa", "Đinh ba");

            grid_ItemLayout.setColumnCount(itemType.size());

            for (int i = 0; i < itemType.size(); i++) {
                View itemProductView = LayoutInflater.from(getContext()).inflate(R.layout.item_type_product, grid_ItemLayout, false);
                TextView textItem = itemProductView.findViewById(R.id.textItem);

                String string = itemType.get(i).toString();
                textItem.setText(string);

                item_View.add(itemProductView);

                if(string.equals("Tất cả")){ // mặc định màu cho type tất cả
                    textItem.setTextColor(getResources().getColor(R.color.white));
                    textItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                }

                textItem.setOnClickListener(v -> { // thay đổi màu chữ, màu nền, và câu lệnh truy vấn
                    for (int j = 0; j < itemType.size(); j++) {
                        TextView currentItem = item_View.get(j).findViewById(R.id.textItem);
                        if (itemType.get(j).toString().equals(string)) {
                            currentItem.setTextColor(getResources().getColor(R.color.white));
                            currentItem.setBackground(getResources().getDrawable(R.drawable.background_type_product));
                            if(string.equals("Tất cả")){
                                query = databaseRef.child("Chậu cây");
                            }else {
                                query = databaseRef.child("Chậu cây").orderByChild("theLoaiSanPham").equalTo(string);

                            }
                            updateRecyclerView(query);

                        } else {
                            currentItem.setTextColor(getResources().getColor(R.color.color_Plant_Type));
                            currentItem.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    }
                });
            }

            for (View viewIT : item_View) { // đổ item lên view
                grid_ItemLayout.addView(viewIT);
            }

        }


        // Thiết lập danh sách và gán dữ liệu lên recycle

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();
        FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position, @NonNull Product model) {

                // Gán dữ liệu
                Picasso.get().load(model.getUrl_Img()).into(holder.img_Item_Product);
                holder.tv_PlantName.setText(model.getTenSanPham());
                holder.tv_PlantType.setText(model.getTheLoaiSanPham());
                holder.tv_PlantPrice.setText(String.valueOf(model.getGiaTien()) + " VNĐ");

                listProduct.add(model);

                holder.itemView.setOnClickListener(v -> {
                    Fragment fragment = new Fragment_Edit_Or_Delete();
                    Bundle bundle1 = new Bundle();

                    bundle1.putInt("id", model.getIdSanPham());
                    fragment.setArguments(bundle1);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                });

            }

            @NonNull
            @Override
            public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ProductAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false));
            }
        };

        rc_Product.setHasFixedSize(true);
        rc_Product.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rc_Product.setAdapter(adapter);
        adapter.startListening();

        // Chuyền fragment
        img_Back.setOnClickListener(v -> {
            adapter.stopListening();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Home()).commit();
        });

        tv_FAB.setOnClickListener(v -> {
            adapter.stopListening();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_AddProduct()).commit();
        });

        return view;
    }

    // phương thức hiển thị product theo từng lựa chọn
    private void updateRecyclerView(Query newQuery) {
        FirebaseRecyclerOptions<Product> newOptions = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(newQuery, Product.class)
                .build();
        FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder> newAdapter = new FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder>(newOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position, @NonNull Product model) {
                Picasso.get().load(model.getUrl_Img()).into(holder.img_Item_Product);
                holder.tv_PlantName.setText(model.getTenSanPham());
                holder.tv_PlantType.setText(model.getTheLoaiSanPham());
                holder.tv_PlantPrice.setText(String.valueOf(model.getGiaTien()) + " VNĐ");
            }

            @NonNull
            @Override
            public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ProductAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false));
            }
        };

        rc_Product.setAdapter(newAdapter);
        newAdapter.startListening();
    }

}
