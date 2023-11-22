package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;

public class Fragment_CamNang extends Fragment {
    ImageView img_Back;
    RecyclerView rc_CamNang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camnang, container, false);
        img_Back = view.findViewById(R.id.img_Back);
        rc_CamNang = view.findViewById(R.id.rc_CamNang);

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();
        });

        rc_CamNang.setOnClickListener(v -> {

        });
        return view;
    }
}
