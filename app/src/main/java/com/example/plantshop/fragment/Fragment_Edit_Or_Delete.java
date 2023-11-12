package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;

public class Fragment_Edit_Or_Delete extends Fragment {

    private ImageView img_BackToProduct, img_Product;
    private TextView tv_NameProduct, tv_Type_Product, tv_TypeOf_Product,
            tv_Price_Prodcut, tv_Size_Product, tv_Brand_Product, tv_Quantity_Product, tv_Describe_Product;
    private Button btn_Delete, btn_Edit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_or_delete, container, false);

        img_BackToProduct = view.findViewById(R.id.img_BackToProduct);
        img_Product = view.findViewById(R.id.img_Product);
        tv_NameProduct = view.findViewById(R.id.tv_NameProduct);
        tv_Type_Product = view.findViewById(R.id.tv_Type_Product);
        tv_TypeOf_Product = view.findViewById(R.id.tv_TypeOf_Product);
        tv_Price_Prodcut = view.findViewById(R.id.tv_Price_Prodcut);
        tv_Size_Product = view.findViewById(R.id.tv_Size_Product);
        tv_Brand_Product = view.findViewById(R.id.tv_Brand_Product);
        tv_Quantity_Product = view.findViewById(R.id.tv_Quantity_Product);
        tv_Describe_Product = view.findViewById(R.id.tv_Describe_Product);
        btn_Delete = view.findViewById(R.id.btn_Delete);
        btn_Edit = view.findViewById(R.id.btn_Edit);

        Bundle getId = getArguments();
        int id = getId.getInt("id");
        img_BackToProduct.setOnClickListener(v -> {
            Fragment fragment = new Fragment_Product();
            Bundle bundle = new Bundle();

            bundle.putString("key", Fragment_Product.key);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });



        return  view;
    }
}
