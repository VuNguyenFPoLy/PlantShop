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
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;

public class Fragment_Product extends Fragment {

    private ImageView img_Back,ic_Cart;
    private TextView tv_Label, tv_All, tv_MoiVe, tv_UaBong, tv_UaMat, tv_FAB;
    private RecyclerView rc_Plant;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        ic_Cart = view.findViewById(R.id.ic_Cart);
        tv_Label = view.findViewById(R.id.tv_Label);
        tv_All = view.findViewById(R.id.tv_All);
        tv_MoiVe = view.findViewById(R.id.tv_MoiVe);
        tv_UaBong = view.findViewById(R.id.tv_UaBong);
        tv_UaMat = view.findViewById(R.id.tv_UaMat);
        tv_FAB = view.findViewById(R.id.tv_FAB);
        rc_Plant = view.findViewById(R.id.rc_Plant);

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Home()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        tv_FAB.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_AddProduct()).commit();
            fragmentTransaction.addToBackStack(null);
        });
        return view;
    }
}
