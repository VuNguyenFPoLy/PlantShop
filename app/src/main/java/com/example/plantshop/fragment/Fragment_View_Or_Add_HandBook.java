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
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_View_Or_Add_HandBook extends Fragment {

    private ImageView img_Back, img_Product;
    private TextView tv_NameProduct, tv_Type_Product, tv_TypeOf_Product, addOrViewStep, addOrViewStage;
    private int getId;
    private ArrayList<Product> listPlant = Fragment_HandBook.listPlant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_or_add_handbook, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_Product = view.findViewById(R.id.img_Product);
        tv_NameProduct = view.findViewById(R.id.tv_NameProduct);
        tv_Type_Product = view.findViewById(R.id.tv_Type_Product);
        tv_TypeOf_Product = view.findViewById(R.id.tv_TypeOf_Product);
        addOrViewStep = view.findViewById(R.id.addOrViewStep);
        addOrViewStage = view.findViewById(R.id.addOrViewStage);

        Bundle bundle = getArguments();
        getId = bundle.getInt("idPlant");

        for (Product pd : listPlant
        ) {
            if(pd.getIdSanPham() == getId){
                Picasso.get().load(pd.getUrl_Img()).into(img_Product);
                tv_NameProduct.setText(pd.getTenSanPham());
                tv_Type_Product.setText(pd.getLoaiSanPham());
                tv_TypeOf_Product.setText(pd.getTheLoaiSanPham());
                break;
            }
        }

        img_Back.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, new Fragment_HandBook()).commit();
        });

        return view;
    }
}
