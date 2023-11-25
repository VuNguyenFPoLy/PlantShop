package com.example.plantshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.plantshop.R;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.fragment.Fragment_Home;
import com.example.plantshop.fragment.Fragment_Notify;
import com.example.plantshop.fragment.Fragment_Profile;
import com.example.plantshop.fragment.Fragment_Search;
import com.example.plantshop.model.Guest;
import com.example.plantshop.model.HistorySearch;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    public static BottomNavigationView bottom_Navigation;
    private AppBarLayout appbarLayout;
    public static ArrayList<Guest> listGuest = new ArrayList<>();
    private DatabaseReference databaseRef_NT, databaseRef_listCartOfNT;
    public static ArrayList<Notification> listNT;
    public static ArrayList<Product> listPurchased;
    public static Guest guest;
    public static int getID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), MainActivity.this);

        bottom_Navigation = findViewById(R.id.bottom_Navigation);
        appbarLayout = findViewById(R.id.appbarLayout);

        Intent intent = getIntent();
        getID = intent.getIntExtra("id", -1);

        listGuest = Activity_DangNhap.listGuest;


        if (getID > 0) {

            for (Guest g : listGuest
            ) {
                if (g.getIdGuest() == getID) {
                    guest = g;
                    break;
                }
            }


            try {
                if (guest.getFullName() != null || !guest.getFullName().equals("Null")) {
                    Toast.makeText(this, "Xin chào " + guest.getFullName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Xin chào Guest", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Toast.makeText(this, "Xin chào Guest", Toast.LENGTH_SHORT).show();
            }

        } else if (getID == 0) {
            Toast.makeText(this, "Xin chào quản lý", Toast.LENGTH_SHORT).show();
        }


        bottom_Navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fr_SelectBottom = null;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);

                if (item.getItemId() == R.id.bt_Home) {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Home();
                } else if (item.getItemId() == R.id.bt_Search) {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Search();
                } else if (item.getItemId() == R.id.bt_Notify) {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Notify();
                } else {
                    item.setTitle(".");
                    fr_SelectBottom = new Fragment_Profile();
                }

                fragmentTransaction.replace(R.id.fr_Layout, fr_SelectBottom).commit();

                return true;
            }
        });

        bottom_Navigation.setSelectedItemId(R.id.bt_Home);

        listNT = new ArrayList<>();
        listPurchased = new ArrayList<>();


        if (getID > 0) {

            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification").child(String.valueOf(getID));
            databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product").child(getID + "/");


            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    listNT.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        listNT.add(notification);
                    }
                    int size = listNT.size();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseRef_listCartOfNT.addListenerForSingleValueEvent(new ValueEventListener() { // lấy list sản phẩm đã đặt hàng
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {



                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {

                        String key = dataSnapshot.getRef().getKey();
                        databaseRef_listCartOfNT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                listPurchased.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()
                                ) {

                                    String key2 = dataSnapshot1.getRef().getKey();

                                    databaseRef_listCartOfNT.child(key).child(key2).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                                            ) {
                                                Product PD = dataSnapshot.getValue(Product.class);
                                                listPurchased.add(PD);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
//
//            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification");
//            databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product");
//
//            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    listNT = new ArrayList<>();
//
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
//                    ) {
//                        Notification notification = dataSnapshot.getValue(Notification.class);
//                        listNT.add(notification);
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        }


    }
}