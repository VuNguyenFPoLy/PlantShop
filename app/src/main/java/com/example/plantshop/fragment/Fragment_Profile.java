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

import com.example.plantshop.R;

public class Fragment_Profile extends Fragment {

    private ImageView img_Avatar;
    private TextView tv_FullName, tv_EmailUser, tv_EditInformation, tv_HandbookPlant, tv_History
            , tv_Help, tv_ChangePass, tv_LogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragnment_profile, container, false);
        return view;
    }
}
