package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;

public class Fragment_Home extends Fragment {

    private TextView lb_Product, tv_ViewAllProduct;
    private RecyclerView rc_Product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lb_Product = view.findViewById(R.id.lb_Product);
        tv_ViewAllProduct = view.findViewById(R.id.tv_ViewAllProduct);
        rc_Product = view.findViewById(R.id.rc_Product);

        tv_ViewAllProduct.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Product()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        return view;
    }
}
