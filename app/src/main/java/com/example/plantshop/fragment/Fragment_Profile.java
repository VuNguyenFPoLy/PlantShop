package com.example.plantshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.Activity_DangNhap;

public class Fragment_Profile extends Fragment {

    private ImageView img_Avatar;
    private TextView tv_FullName, tv_EmailUser, tv_EditInformation, tv_HandbookPlant, tv_History
            , tv_Help, tv_ChangePass, tv_LogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragnment_profile, container, false);

        img_Avatar = view.findViewById(R.id.img_Avatar);
        tv_FullName = view.findViewById(R.id.tv_FullName);
        tv_EmailUser = view.findViewById(R.id.tv_EmailUser);
        tv_EditInformation = view.findViewById(R.id.tv_EditInformation);
        tv_HandbookPlant = view.findViewById(R.id.tv_HandbookPlant);
        tv_History = view.findViewById(R.id.tv_History);
        tv_Help = view.findViewById(R.id.tv_Help);
        tv_ChangePass = view.findViewById(R.id.tv_ChangePass);
        tv_LogOut = view.findViewById(R.id.tv_LogOut);

        tv_LogOut.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_DangNhap.class);
            startActivity(intent);
        });
        return view;
    }
}
