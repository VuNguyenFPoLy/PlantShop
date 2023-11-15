package com.example.plantshop.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.adapter.SearchAdapter;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Fragment_Search extends Fragment {

    private ImageView img_Back;
    private EditText edt_Search;
    private RecyclerView rc_SearchHistory;
    private ArrayList<Product> allProductList = new ArrayList<>();
    private ArrayList<Product> fillList = new ArrayList<>();
    private SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        edt_Search = view.findViewById(R.id.edt_Search);
        rc_SearchHistory = view.findViewById(R.id.rc_SearchHistory);

        for (Product product : Fragment_Home.listPlant
        ) {
            allProductList.add(product);
        }

        for (Product product : Fragment_Home.listPots
        ) {
            allProductList.add(product);
        }

        for (Product product : Fragment_Home.listTools
        ) {
            allProductList.add(product);
        }
        edt_Search.addTextChangedListener(new SearchProduct(edt_Search, allProductList));

        img_Back.setOnClickListener(v -> {
            Fragment fragment = new Fragment_Home();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });

        rc_SearchHistory.setHasFixedSize(true);
        rc_SearchHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return view;
    }

    private class SearchProduct implements TextWatcher {

        private EditText searchName;
        private ArrayList<Product> fillList;

        public SearchProduct(EditText searchName, ArrayList<Product> fillList) {
            this.searchName = searchName;
            this.fillList = fillList;
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

            int size = fillList.size();
            for (Product pd : fillList
            ) {

                if (findName.toLowerCase().startsWith(pd.getTenSanPham().toLowerCase())) {

                    fillList.add(pd);
                    searchAdapter = new SearchAdapter(getContext(), fillList);
                    rc_SearchHistory.setAdapter(searchAdapter);

                    break;
                }

            }


        }
    }

}
