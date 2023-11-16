package com.example.plantshop.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

    private TextView tv_ViewAllPlant, tv_ViewAllPots, tv_ViewAllTools;
    private ImageView img_cart;
    private GridLayout gridLayout_Plant, gridLayout_Pots, gridLayout_Tool;
    private DAO_Product dao_product;
    public static ArrayList<Product> listPlant, listPots, listTools;
    private int size = -1;
    boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_ViewAllPlant = view.findViewById(R.id.tv_ViewAllPlant);
        tv_ViewAllPots = view.findViewById(R.id.tv_ViewAllPots);
        tv_ViewAllTools = view.findViewById(R.id.tv_ViewAllTools);
        img_cart = view.findViewById(R.id.img_cart);

        gridLayout_Plant = view.findViewById(R.id.gridLayout_Plant);
        gridLayout_Pots = view.findViewById(R.id.gridLayout_Pots);
        gridLayout_Tool = view.findViewById(R.id.gridLayout_Tool);

        // thêm gạch chân textview
        tv_ViewAllPlant.setPaintFlags(tv_ViewAllPlant.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_ViewAllTools.setPaintFlags(tv_ViewAllTools.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_ViewAllPots.setPaintFlags(tv_ViewAllPots.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (MainActivity.getID == 0) {
            img_cart.setVisibility(View.GONE);
        }

        listPlant = new ArrayList<>();
        listPots = new ArrayList<>();
        listTools = new ArrayList<>();

        listPlant = dao_product.getListPlant();
        listPots = DAO_Product.getListPots();
        listTools = DAO_Product.getListTools();


        List<View> ViewItemPlant = new ArrayList<>();
        List<View> ViewItemPots = new ArrayList<>();
        List<View> ViewItemTools = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (firstTime) {
                    gridLayout_Plant.removeAllViews();
                    gridLayout_Pots.removeAllViews();
                    gridLayout_Tool.removeAllViews();
                    firstTime = false;
                }

                if (listPlant.size() > 0) { // đổ dữ liệu lên gridlayout cây trồng
                    handler.removeCallbacks(this);


                    for (Product product : listPlant) {

                        View itemPlantView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_gridlayout, gridLayout_Plant, false);

                        ImageView img_Item_Product_Gr = itemPlantView.findViewById(R.id.img_Item_Product_Gr);
                        TextView tv_ItemName_Gr = itemPlantView.findViewById(R.id.tv_ItemName_Gr);
                        TextView tv_ItemType_Gr = itemPlantView.findViewById(R.id.tv_ItemType_Gr);
                        TextView tv_ItemPrice_Gr = itemPlantView.findViewById(R.id.tv_ItemPrice_Gr);

                        Picasso.get().load(product.getUrl_Img()).into(img_Item_Product_Gr);
                        tv_ItemName_Gr.setText(product.getTenSanPham());
                        tv_ItemType_Gr.setText(product.getTheLoaiSanPham());
                        tv_ItemPrice_Gr.setText(String.valueOf(product.getGiaTien()));

                        ViewItemPlant.add(itemPlantView);

                        itemPlantView.setOnClickListener(v -> {
                            Fragment fragment = new Fragment_Edit_Or_Delete();
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("id", product.getIdSanPham());
                            bundle1.putString("from", "home");
                            bundle1.putString("type", product.getLoaiSanPham());
                            fragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                        });

                    }

                    for (View viewPlant : ViewItemPlant) {
                        gridLayout_Plant.addView(viewPlant);
                    }

                } else {
                    handler.postDelayed(this, 500);
                }

                if (listPots.size() > 0) { // đổ dữ liệu lên gridlayout chậu cây

                    for (Product product : listPots) {

                        View itemPotsView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_gridlayout, gridLayout_Pots, false);

                        ImageView img_Item_Product_Gr = itemPotsView.findViewById(R.id.img_Item_Product_Gr);
                        TextView tv_ItemName_Gr = itemPotsView.findViewById(R.id.tv_ItemName_Gr);
                        TextView tv_ItemType_Gr = itemPotsView.findViewById(R.id.tv_ItemType_Gr);
                        TextView tv_ItemPrice_Gr = itemPotsView.findViewById(R.id.tv_ItemPrice_Gr);

                        Picasso.get().load(product.getUrl_Img()).into(img_Item_Product_Gr);
                        tv_ItemName_Gr.setText(product.getTenSanPham());
                        tv_ItemType_Gr.setText(product.getTheLoaiSanPham());
                        tv_ItemPrice_Gr.setText(String.valueOf(product.getGiaTien()));

                        ViewItemPots.add(itemPotsView);

                        itemPotsView.setOnClickListener(v -> {
                            Fragment fragment = new Fragment_Edit_Or_Delete();
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("id", product.getIdSanPham());
                            bundle1.putString("from", "home");
                            bundle1.putString("type", product.getLoaiSanPham());
                            fragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                        });

                    }

                    for (View viewPots : ViewItemPots) {
                        gridLayout_Pots.addView(viewPots);
                    }

                }

                if (listTools.size() > 0) { // đổ dữ liệu lên gridlayout dụng cụ

                    for (Product product : listTools) {

                        View itemToolsView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_gridlayout, gridLayout_Tool, false);

                        ImageView img_Item_Product_Gr = itemToolsView.findViewById(R.id.img_Item_Product_Gr);
                        TextView tv_ItemName_Gr = itemToolsView.findViewById(R.id.tv_ItemName_Gr);
                        TextView tv_ItemType_Gr = itemToolsView.findViewById(R.id.tv_ItemType_Gr);
                        TextView tv_ItemPrice_Gr = itemToolsView.findViewById(R.id.tv_ItemPrice_Gr);

                        Picasso.get().load(product.getUrl_Img()).into(img_Item_Product_Gr);
                        tv_ItemName_Gr.setText(product.getTenSanPham());
                        tv_ItemType_Gr.setText(product.getTheLoaiSanPham());
                        tv_ItemPrice_Gr.setText(String.valueOf(product.getGiaTien()));

                        ViewItemTools.add(itemToolsView);

                        itemToolsView.setOnClickListener(v -> {
                            Fragment fragment = new Fragment_Edit_Or_Delete();
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("id", product.getIdSanPham());
                            bundle1.putString("from", "home");
                            bundle1.putString("type", product.getLoaiSanPham());
                            fragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                        });

                    }

                    for (View viewTools : ViewItemTools) {
                        gridLayout_Tool.addView(viewTools);
                    }

                }


            }

        };

        handler.post(runnable);


        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        tv_ViewAllPlant.setOnClickListener(v -> { // xem tất cả cây
            Fragment fragment = new Fragment_Product();
            bundle.putString("key", tv_ViewAllPlant.getText().toString());
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fr_Layout, fragment).commit();
        });

        tv_ViewAllPots.setOnClickListener(v -> { // xem tất cả chậu
            Fragment fragment = new Fragment_Product();
            bundle.putString("key", tv_ViewAllPots.getText().toString());
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fr_Layout, fragment).commit();
        });

        tv_ViewAllTools.setOnClickListener(v -> { // xem tất cả dụng cụ
            Fragment fragment = new Fragment_Product();
            bundle.putString("key", tv_ViewAllTools.getText().toString());
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fr_Layout, fragment).commit();
        });


        return view;
    }
}
