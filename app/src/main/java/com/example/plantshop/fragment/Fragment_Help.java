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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.adapter.HelpAdapter;
import com.example.plantshop.model.Help;

import java.util.ArrayList;


public class Fragment_Help extends Fragment {
    private ImageView img_Back;
    private RecyclerView rc_Help;
    public static ArrayList<Help> listHelp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        img_Back = view.findViewById(R.id.img_Back);
        rc_Help = view.findViewById(R.id.rc_help);

        rc_Help.setHasFixedSize(true);
        rc_Help.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        listHelp = new ArrayList<>();
        rc_Help.setAdapter(new HelpAdapter(listHelp, getContext()));

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();
        });

        return view;
    }
}
