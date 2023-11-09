package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;

public class Fragment_Search extends Fragment {

    private ImageView img_Back;
    private EditText edt_Search;
    private RecyclerView rc_SearchHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        edt_Search = view.findViewById(R.id.edt_Search);
        rc_SearchHistory = view.findViewById(R.id.rc_SearchHistory);

        return view;
    }
}
