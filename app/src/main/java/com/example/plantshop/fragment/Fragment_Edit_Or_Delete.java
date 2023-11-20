package com.example.plantshop.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_Edit_Or_Delete extends Fragment {

    private ImageView img_BackToProduct, img_Product;
    private TextView tv_NameProduct, tv_Type_Product, tv_TypeOf_Product,
            tv_Price_Product, tv_Size_Product, tv_Brand_Product, tv_Quantity_Product, tv_Describe_Product;
    private Button btn_Delete, btn_Edit;
    private ArrayList<Product> listProduct;
    public static int id;
    private boolean check = false;
    private String getFrom, type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_or_delete, container, false);

        img_BackToProduct = view.findViewById(R.id.img_BackToProduct);
        img_Product = view.findViewById(R.id.img_Product);
        tv_NameProduct = view.findViewById(R.id.tv_NameProduct);
        tv_Type_Product = view.findViewById(R.id.tv_Type_Product);
        tv_TypeOf_Product = view.findViewById(R.id.tv_TypeOf_Product);
        tv_Price_Product = view.findViewById(R.id.tv_Price_Product);
        tv_Size_Product = view.findViewById(R.id.tv_Size_Product);
        tv_Brand_Product = view.findViewById(R.id.tv_Brand_Product);
        tv_Quantity_Product = view.findViewById(R.id.tv_Quantity_Product);
        tv_Describe_Product = view.findViewById(R.id.tv_Describe_Product);
        btn_Delete = view.findViewById(R.id.btn_Delete);
        btn_Edit = view.findViewById(R.id.btn_Edit);

        Bundle getData = getArguments(); // lấy dữ liệu khi fragment khác chuyển đến
        id = getData.getInt("id");
        getFrom = getData.getString("from");
        type = getData.getString("type");

        if(getFrom.equals("product")){ // kiểm tra dữ liệu từ fragment nào chuyển đến
            listProduct = Fragment_Product.listProduct;
        }else {
            if(type.equals("Cây trồng")){
                listProduct = Fragment_Home.listPlant;
            } else if (type.equals("Chậu cây")) {
                listProduct = Fragment_Home.listPots;
            }else {
                listProduct = Fragment_Home.listTools;
            }
        }

        // Gán dữ liệu
        for (Product pd : listProduct
        ) {
            if (id == pd.getIdSanPham()) {

                Picasso.get().load(pd.getUrl_Img()).into(img_Product);
                tv_NameProduct.setText(pd.getTenSanPham());
                tv_Type_Product.setText(pd.getLoaiSanPham());
                tv_TypeOf_Product.setText(pd.getTheLoaiSanPham());
                tv_Price_Product.setText(String.valueOf(pd.getGiaTien()) + " VNĐ");
                tv_Size_Product.setText(pd.getKichCo());
                tv_Brand_Product.setText(pd.getXuatXu());
                tv_Quantity_Product.setText("Còn " + pd.getSoLuong() + " sản phẩm");
                tv_Describe_Product.setText(pd.getMoTa());

            }
        }

        btn_Edit.setOnClickListener(v -> {
            Fragment fragment = new Fragment_AddProduct();
            Bundle bundle = new Bundle();
            bundle.putString("edit", "edit");
            bundle.putString("from", getFrom);
            String type = tv_Type_Product.getText().toString();

            if(type.equals("Cây trồng")){
                Fragment_Product.key = "Xem thêm cây trồng";
            } else if (type.equals("Chậu cây")) {
                Fragment_Product.key = "Xem thêm chậu cây";
            }else {
                Fragment_Product.key = "Xem thêm dụng cụ";
            }

            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();

        });

        img_BackToProduct.setOnClickListener(v -> {
            if(getFrom.equals("product")){
                Fragment fragment = new Fragment_Product();
                Bundle bundle = new Bundle();

                bundle.putString("key", Fragment_Product.key);
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
            }else if (getFrom.equals("home")){
                MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
            } else if (getFrom.equals("search")) {
                MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Search);
            }

        });

        // xoá sản phẩm
        btn_Delete.setOnClickListener(v -> {

            Dialog bottomDialog = new Dialog(getContext());
            bottomDialog.setContentView(R.layout.layout_sheet_bottom);

            Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
            TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);

            String typeProduct = tv_Type_Product.getText().toString();
            btn_Accept.setOnClickListener(v1 -> { // chấp nhận xoá

                if(typeProduct.equals("Cây trồng")){
                    DAO_Product.deletePlant(id);
                    check = true;
                } else if (typeProduct.equals("Chậu cây")) {
                    DAO_Product.deletePots(id);
                    check = true;
                }else {
                    DAO_Product.deleteTools(id);
                    check = true;
                }

                if(check){
                    Fragment fragment = new Fragment_Product();
                    Bundle bundle = new Bundle();
                    bundle.putString("key", Fragment_Product.key);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                    bottomDialog.dismiss();
                    Toast.makeText(getContext(), "Đã xoá " + tv_NameProduct.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            });

            tv_Cancel.setOnClickListener(v1 -> { // huỷ xoá
                bottomDialog.dismiss();
            });

            bottomDialog.show();
            bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
            bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ
        });


        return view;
    }
}
