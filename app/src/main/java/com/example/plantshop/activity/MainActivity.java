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


    public static BottomNavigationView bottom_Navigation;
    private AppBarLayout appbarLayout;
    public static int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), MainActivity.this);

        bottom_Navigation = findViewById(R.id.bottom_Navigation);
        appbarLayout = findViewById(R.id.appbarLayout);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);


        bottom_Navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fr_SelectBottom = null;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);

                if(item.getItemId() == R.id.bt_Home){
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Home();
                } else if (item.getItemId() == R.id.bt_Search) {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Search();
                } else if (item.getItemId() == R.id.bt_Notify) {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Notify();
                }else {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Profile();
                }

                fragmentTransaction.replace(R.id.fr_Layout, fr_SelectBottom).commit();

                return true;
            }
        });

        bottom_Navigation.setSelectedItemId(R.id.bt_Home);

    }
}