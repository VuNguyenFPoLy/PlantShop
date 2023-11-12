package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Fragment_Product extends Fragment {

    private ImageView img_Back, ic_Cart;
    private TextView tv_Label, tv_All, tv_UaBong, tv_UaMat, tv_UaSang, tv_UaToi, tv_FAB;
    private RecyclerView rc_Product;
    private DAO_Product dao_product;
    private boolean checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi;
    private Query query;
    public static String key;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        ic_Cart = view.findViewById(R.id.ic_Cart);
        tv_Label = view.findViewById(R.id.tv_Label);
        tv_All = view.findViewById(R.id.tv_All);
        tv_UaBong = view.findViewById(R.id.tv_UaBong);
        tv_UaMat = view.findViewById(R.id.tv_UaMat);
        tv_UaSang = view.findViewById(R.id.tv_UaSang);
        tv_UaToi = view.findViewById(R.id.tv_UaToi);
        tv_FAB = view.findViewById(R.id.tv_FAB);
        rc_Product = view.findViewById(R.id.rc_Product);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product");

        Bundle bundle = getArguments();
        key = bundle.getString("key");


        if(key.equals("Xem thêm cây trồng")){
            query = databaseRef.child("Cây trồng");
        }

        tv_All.setOnClickListener(v -> {
            checktv_All = true;
            checktv_UaBong = false;
            checktv_UaMat = false;
            checktv_UaSang = false;
            checktv_UaToi = false;
            changeTV(checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi);
            query = databaseRef.child("Cây trồng");
            updateRecyclerView(query);
        });


        tv_UaBong.setOnClickListener(v -> {
            checktv_All = false;
            checktv_UaBong = true;
            checktv_UaMat = false;
            checktv_UaSang = false;
            checktv_UaToi = false;
            changeTV(checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi);
            query = databaseRef.child("Cây trồng").orderByChild("theLoaiSanPham").equalTo("Ưa bóng");
            updateRecyclerView(query);
        });

        tv_UaMat.setOnClickListener(v -> {
            checktv_All = false;
            checktv_UaBong = false;
            checktv_UaMat = true;
            checktv_UaSang = false;
            checktv_UaToi = false;
            changeTV(checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi);
            query = databaseRef.child("Cây trồng").orderByChild("theLoaiSanPham").equalTo("Ưa mát");
            updateRecyclerView(query);
        });

        tv_UaSang.setOnClickListener(v -> {
            checktv_All = false;
            checktv_UaBong = false;
            checktv_UaMat = false;
            checktv_UaSang = true;
            checktv_UaToi = false;
            changeTV(checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi);
            query = databaseRef.child("Cây trồng").orderByChild("theLoaiSanPham").equalTo("Ưa sáng");
            updateRecyclerView(query);
        });

        tv_UaToi.setOnClickListener(v -> {
            checktv_All = false;
            checktv_UaBong = false;
            checktv_UaMat = false;
            checktv_UaSang = false;
            checktv_UaToi = true;
            changeTV(checktv_All, checktv_UaBong, checktv_UaMat, checktv_UaSang, checktv_UaToi);
            query = databaseRef.child("Cây trồng").orderByChild("theLoaiSanPham").equalTo("Ưa tối");
            updateRecyclerView(query);
        });


        if (MainActivity.id <= 0) {
            ic_Cart.setVisibility(View.GONE);
        }

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();
        FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductAdapter.ViewHolder>(options) {
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

        rc_Product.setHasFixedSize(true);
        rc_Product.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rc_Product.setAdapter(adapter);
        adapter.startListening();

        img_Back.setOnClickListener(v -> {
            adapter.stopListening();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Home()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        tv_FAB.setOnClickListener(v -> {
            adapter.stopListening();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_AddProduct()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        return view;
    }

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


    public void changeTV(boolean checktv_All, boolean checktv_UaBong, boolean checktv_UaMat, boolean checktv_UaSang, boolean checktv_UaToi){
        if(checktv_All){
            tv_All.setTextColor(getResources().getColor(R.color.white));
            tv_All.setBackground(getResources().getDrawable(R.drawable.background_type_product));
        }else {
            tv_All.setTextColor(getResources().getColor(R.color.color_Plant_Type));
            tv_All.setBackgroundColor(getResources().getColor(R.color.white));
        }


        if(checktv_UaBong){
            tv_UaBong.setTextColor(getResources().getColor(R.color.white));
            tv_UaBong.setBackground(getResources().getDrawable(R.drawable.background_type_product));
        }else {
            tv_UaBong.setTextColor(getResources().getColor(R.color.color_Plant_Type));
            tv_UaBong.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if(checktv_UaMat){
            tv_UaMat.setTextColor(getResources().getColor(R.color.white));
            tv_UaMat.setBackground(getResources().getDrawable(R.drawable.background_type_product));
        }else {
            tv_UaMat.setTextColor(getResources().getColor(R.color.color_Plant_Type));
            tv_UaMat.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if(checktv_UaSang){
            tv_UaSang.setTextColor(getResources().getColor(R.color.white));
            tv_UaSang.setBackground(getResources().getDrawable(R.drawable.background_type_product));
        }else {
            tv_UaSang.setTextColor(getResources().getColor(R.color.color_Plant_Type));
            tv_UaSang.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if(checktv_UaToi){
            tv_UaToi.setTextColor(getResources().getColor(R.color.white));
            tv_UaToi.setBackground(getResources().getDrawable(R.drawable.background_type_product));
        }else {
            tv_UaToi.setTextColor(getResources().getColor(R.color.color_Plant_Type));
            tv_UaToi.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

}
