package com.example.plantshop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.NotifyAdapter;
import com.example.plantshop.firebase.DAO_Notify;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_Notify extends Fragment implements NotifyAdapter.OnItemClickListener {

    private ImageView img_Back;
    private TextView tv_Notify;
    private RecyclerView rc_Notify;
    private DAO_Notify daoNotify;
    private ArrayList<Notification> listNotify;
    ArrayList<Product> listPurchased;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        tv_Notify = view.findViewById(R.id.tv_Notify);
        rc_Notify = view.findViewById(R.id.rc_Notify);

        rc_Notify.setHasFixedSize(true);
        rc_Notify.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        daoNotify = new DAO_Notify();
        listNotify = new ArrayList<>();


        getList();



        return view;
    }

    public void getList() {

        if (MainActivity.getID > 0) {
            DatabaseReference databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification").child(String.valueOf(MainActivity.getID));
            DatabaseReference databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product").child(MainActivity.getID + "/");

            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listNotify.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        listNotify.add(notification);
                    }

                    // Kiểm tra null trước khi gọi hàm hiển thị RecyclerView
                    showRecyclerViewIfDataAvailable();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi
                }
            });

            databaseRef_listCartOfNT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listPurchased = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getRef().getKey();

                        databaseRef_listCartOfNT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    String key2 = dataSnapshot1.getRef().getKey();

                                    databaseRef_listCartOfNT.child(key).child(key2).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                Product PD = dataSnapshot.getValue(Product.class);
                                                listPurchased.add(PD);
                                                MainActivity.setNumberBadge(listPurchased.size());
                                            }
                                            showRecyclerViewIfDataAvailable();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Xử lý khi có lỗi
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý khi có lỗi
                            }
                        });
                    }

                    // Kiểm tra null trước khi gọi hàm hiển thị RecyclerView

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi
                }
            });
        } else {
            // Xử lý khi MainActivity.getID < 0 (admin)
            DatabaseReference databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification");
            DatabaseReference databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product");

            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listNotify = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getRef().getKey();

                        databaseRef_NT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Notification notification = dataSnapshot.getValue(Notification.class);
                                    listNotify.add(notification);
                                }
                                showRecyclerViewIfDataAvailable();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý khi có lỗi
                            }
                        });
                    }

                    // Kiểm tra null trước khi gọi hàm hiển thị RecyclerView

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi
                }
            });

            databaseRef_listCartOfNT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listPurchased = new ArrayList<>();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getRef().getKey();

                        databaseRef_listCartOfNT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    String key2 = dataSnapshot1.getRef().getKey();

                                    databaseRef_listCartOfNT.child(key).child(key2).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                                String key3 = dataSnapshot2.getRef().getKey();

                                                databaseRef_listCartOfNT.child(key).child(key2).child(key3).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot dataSnapshot3 : snapshot.getChildren()) {
                                                            Product PD = dataSnapshot3.getValue(Product.class);
                                                            listPurchased.add(PD);
                                                            MainActivity.setNumberBadge(listPurchased.size());
                                                        }
                                                        showRecyclerViewIfDataAvailable();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        // Xử lý khi có lỗi
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Xử lý khi có lỗi
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý khi có lỗi
                            }
                        });
                    }

                    // Kiểm tra null trước khi gọi hàm hiển thị RecyclerView

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý khi có lỗi
                }
            });
        }

//        if (listNotify.size() > 0) {
//            tv_Notify.setVisibility(View.GONE);
//        }else {
//            tv_Notify.setVisibility(View.VISIBLE);
//        }

    }

    // Hàm kiểm tra và hiển thị RecyclerView
    private void showRecyclerViewIfDataAvailable() {
        if (listNotify != null && listPurchased != null && listNotify.size() > 0 && listPurchased.size() > 0) {

            NotifyAdapter notifyAdapter = new NotifyAdapter(getContext(), listNotify, listPurchased, Fragment_Notify.this);



            rc_Notify.setAdapter(notifyAdapter);
        }
    }




    @Override
    public void onItemClick(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
    }


}
