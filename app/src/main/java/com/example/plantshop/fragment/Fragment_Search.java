package com.example.plantshop.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.SearchAdapter;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Fragment_Search extends Fragment implements SearchAdapter.OnItemClickListener {

    private ImageView img_Back;
    private EditText edt_Search;
    private RecyclerView rc_SearchHistory;
    private ArrayList<Product> allProductList = new ArrayList<>();
    private ArrayList<Product> fillList = new ArrayList<>();
    private SearchAdapter searchAdapter;
    public static TextView lb_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        edt_Search = view.findViewById(R.id.edt_Search);
        rc_SearchHistory = view.findViewById(R.id.rc_SearchHistory);
        lb_search = view.findViewById(R.id.lb_search);


        for (Product product : Fragment_Home.listPlant
        ) {
            allProductList.add(product);
        }

        for (Product product : Fragment_Home.listPots               // Lấy tất cả các sản phẩm thêm vào 1 arraylist
        ) {
            allProductList.add(product);
        }

        for (Product product : Fragment_Home.listTools
        ) {
            allProductList.add(product);
        }
        edt_Search.addTextChangedListener(new SearchProduct(edt_Search, allProductList));

        img_Back.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
        });

        rc_SearchHistory.setHasFixedSize(true);
        rc_SearchHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return view;
    }

    @Override
    public void onItemClick(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
    }


    private class SearchProduct implements TextWatcher {

        private EditText searchName;
        private ArrayList<Product> fillList;
        private ArrayList<Product> listFind = new ArrayList<>();
        private SearchAdapter searchAdapter; // Đưa adapter ra khỏi vòng lặp

        public SearchProduct(EditText searchName, ArrayList<Product> fillList) {
            this.searchName = searchName;
            this.fillList = fillList;
            this.searchAdapter = new SearchAdapter(getContext(), listFind, Fragment_Search.this);
            rc_SearchHistory.setAdapter(searchAdapter);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String findName = editable.toString().trim();

            // Xoá sạch danh sách trước khi thêm các sản phẩm mới
            listFind.clear();

            // Duyệt qua danh sách fillList để tìm kiếm
            for (Product pd : fillList) {
                if (pd.getTenSanPham().toLowerCase().startsWith(findName.toLowerCase())) {
                    listFind.add(pd);
                }
            }

            if(findName.isEmpty() || findName.equals("") || findName == null){
                listFind.clear();
            }
            if(listFind.size() > 0){
                Fragment_Search.lb_search.setVisibility(View.GONE);
            }else {
                Fragment_Search.lb_search.setVisibility(View.VISIBLE);

            }

            // Cập nhật adapter sau khi hoàn thành việc tìm kiếm
            searchAdapter.notifyDataSetChanged();
        }
    }


}
