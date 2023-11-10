package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantshop.R;

public class Fragment_AddProduct extends Fragment {

    private ImageView img_Back, img_addProduct;
    private EditText edt_NameProduct, edt_Price, edt_Size, edt_Brand, edt_Quantity, edt_Describe;
    private TextView lb_Notify;
    private Button btn_Cancel, btn_Submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_add_product, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_addProduct = view.findViewById(R.id.img_addProduct);
        edt_NameProduct = view.findViewById(R.id.edt_NameProduct);
        edt_Price = view.findViewById(R.id.edt_Price);
        edt_Size = view.findViewById(R.id.edt_Size);
        edt_Brand = view.findViewById(R.id.edt_Brand);
        edt_Quantity = view.findViewById(R.id.edt_Quantity);
        edt_Describe = view.findViewById(R.id.edt_Describe);
        lb_Notify = view.findViewById(R.id.lb_Notify);
        btn_Cancel = view.findViewById(R.id.btn_Cancel);
        btn_Submit = view.findViewById(R.id.btn_Submit);

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Product()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        btn_Cancel.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Product()).commit();
            fragmentTransaction.addToBackStack(null);
        });


        return view;
    }
}
