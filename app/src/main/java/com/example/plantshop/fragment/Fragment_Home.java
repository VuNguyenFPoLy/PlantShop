package com.example.plantshop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.ProductAdapter;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

    private TextView tv_ViewAllPlant, tv_ViewAllPots, tv_ViewAllTools;
    private ImageView img_cart;
    private GridLayout gridLayout_Plant, gridLayout_Pots, gridLayout_Tool;
    private DAO_Product dao_product;
    private ArrayList<Product> listPlant;
    private int size = -1;
    boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_ViewAllPlant = view.findViewById(R.id.tv_ViewAllPlant);
        tv_ViewAllPots = view.findViewById(R.id.tv_ViewAllPots);
        tv_ViewAllTools = view.findViewById(R.id.tv_ViewAllTools);
        gridLayout_Plant = view.findViewById(R.id.gridLayout_Plant);
        img_cart = view.findViewById(R.id.img_cart);

        gridLayout_Pots = view.findViewById(R.id.gridLayout_Pots);
        gridLayout_Tool = view.findViewById(R.id.gridLayout_Tool);

        if(MainActivity.id == 0){
            img_cart.setVisibility(View.GONE);
        }

        listPlant = new ArrayList<>();
        listPlant = dao_product.getListPlant();


        Handler handler = new Handler(Looper.getMainLooper());

        List<View> productViews = new ArrayList<>();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (firstTime) {
                    gridLayout_Plant.removeAllViews();
                    firstTime = false;
                }

                int size = listPlant.size();

                if (size > 0) {

                    handler.removeCallbacks(this);

                    for (Product product : listPlant) {

                        View itemProductView = LayoutInflater.from(getContext()).inflate(R.layout.item_product_gridlayout, gridLayout_Plant, false);

                        ImageView img_Item_Product_Gr = itemProductView.findViewById(R.id.img_Item_Product_Gr);
                        TextView tv_PlantName_Gr = itemProductView.findViewById(R.id.tv_PlantName_Gr);
                        TextView tv_PlantType_Gr = itemProductView.findViewById(R.id.tv_PlantType_Gr);
                        TextView tv_PlantPrice_Gr = itemProductView.findViewById(R.id.tv_PlantPrice_Gr);

                        Picasso.get().load(product.getUrl_Img()).into(img_Item_Product_Gr);
                        tv_PlantName_Gr.setText(product.getTenSanPham());
                        tv_PlantType_Gr.setText(product.getTheLoaiSanPham());
                        tv_PlantPrice_Gr.setText(String.valueOf(product.getGiaTien()));

                        productViews.add(itemProductView);


                    }

                    for (View view : productViews) {

                        gridLayout_Plant.addView(view);
                    }

                } else {
                    handler.postDelayed(this, 500);
                }

            }

        };

        handler.post(runnable);



        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        tv_ViewAllPlant.setOnClickListener(v -> {
            Fragment fragment = new Fragment_Product();
            bundle.putString("key", tv_ViewAllPlant.getText().toString());
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fr_Layout, fragment).commit();
            fragmentTransaction.addToBackStack(null);
        });


        return view;
    }
}
