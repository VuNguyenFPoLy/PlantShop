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
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;

import java.util.Calendar;

public class Fragment_Notify extends Fragment {

    private ImageView img_Back;
    private TextView tv_Notify;
    private RecyclerView rc_Notify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        tv_Notify = view.findViewById(R.id.tv_Notify);
        rc_Notify = view.findViewById(R.id.rc_Notify);

//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int month = calendar.get(Calendar.MONTH);
//        int year = calendar.get(Calendar.YEAR);
//
//        String date = day + ""

        return view;
    }
}
