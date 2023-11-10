package com.example.plantshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.plantshop.R;
import com.example.plantshop.fragment.Fragment_Home;
import com.example.plantshop.fragment.Fragment_Notify;
import com.example.plantshop.fragment.Fragment_Profile;
import com.example.plantshop.fragment.Fragment_Search;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    private ImageView img_cart;
    private FrameLayout fr_Layout;
    private BottomNavigationView bottom_Navigation;
    private AppBarLayout appbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), MainActivity.this);

        img_cart = findViewById(R.id.img_cart);
        fr_Layout = findViewById(R.id.fr_Layout);
        bottom_Navigation = findViewById(R.id.bottom_Navigation);
        appbarLayout = findViewById(R.id.appbarLayout);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == 0){
            img_cart.setVisibility(View.GONE);
        }


        bottom_Navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fr_SelectBottom = null;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);

                if(item.getItemId() == R.id.bt_Home){
                    fr_SelectBottom = new Fragment_Home();
                } else if (item.getItemId() == R.id.bt_Search) {
                    fr_SelectBottom = new Fragment_Search();
                } else if (item.getItemId() == R.id.bt_Notify) {
                    fr_SelectBottom = new Fragment_Notify();
                }else {
                    fr_SelectBottom = new Fragment_Profile();
                }

                fragmentTransaction.replace(R.id.fr_Layout, fr_SelectBottom).commit();

                return true;
            }
        });

        bottom_Navigation.setSelectedItemId(R.id.bt_Home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Kiểm tra Fragment hiện tại
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fr_Layout);
                Log.d("MainActivity", "onBackStackChanged: " + currentFragment.getClass().getSimpleName());

                // Hiển thị hoặc ẩn AppBarLayout dựa trên Fragment hiện tại
                if (currentFragment instanceof Fragment_Home) {
                    appbarLayout.setVisibility(View.VISIBLE);
                } else {
                    appbarLayout.setVisibility(View.GONE);
                }
            }
        });



    }
}