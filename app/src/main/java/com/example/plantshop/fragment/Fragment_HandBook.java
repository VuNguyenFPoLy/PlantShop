package com.example.plantshop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.HandBookAdapter;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;

import java.util.ArrayList;

public class Fragment_HandBook extends Fragment implements HandBookAdapter.OnItemClickListener {
    private ImageView img_Back;
    private RecyclerView rc_CamNang;
    public static ArrayList<Product> listPlant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handbook, container, false);
        img_Back = view.findViewById(R.id.img_Back);
        rc_CamNang = view.findViewById(R.id.rc_CamNang);

        rc_CamNang.setHasFixedSize(true);
        rc_CamNang.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        listPlant = DAO_Product.getListPlant();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() { // chờ load danh sách từ mạng
            @Override
            public void run() {

                if(listPlant.size() > 0){
                    handler.removeCallbacks(this);
                    HandBookAdapter adapter = new HandBookAdapter(getContext(), listPlant, Fragment_HandBook.this);
                    rc_CamNang.setAdapter(adapter);
                }else {
                    handler.postDelayed(this, 500);
                }
            }
        };

        handler.postDelayed(runnable, 500);


        img_Back.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Profile);
        });

        return view;
    }

    @Override
    public void onItemClick(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
    }
}
