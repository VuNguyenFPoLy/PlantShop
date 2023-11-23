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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.NotifyAdapter;
import com.example.plantshop.model.Notification;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

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

        rc_Notify.setHasFixedSize(true);
        rc_Notify.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        NotifyAdapter notifyAdapter = new NotifyAdapter(getContext(), MainActivity.listNT, MainActivity.listPurchased);

        if(MainActivity.listNT.size() > 0){
            tv_Notify.setVisibility(View.GONE);
        }

        rc_Notify.setAdapter(notifyAdapter);

        return view;
    }
}
