package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO_Notify {

    private DatabaseReference databaseRef_NT, databaseRef_listCartOfNT;
    private ArrayList<Notification> listNotify;
    private ArrayList<Product> listPurchased;

    public DAO_Notify() {

        listNotify = new ArrayList<>();
        listPurchased = new ArrayList<>();

        if (MainActivity.getID > 0) {
            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification").child(String.valueOf(MainActivity.getID));
            databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product").child(MainActivity.getID + "/");

            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    listNotify.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        listNotify.add(notification);
                    }

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
                                                MainActivity.setNumberBadge(listPurchased.size());

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
        } else {
            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification");
            databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product");

            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {

                        String key = dataSnapshot.getRef().getKey();

                        databaseRef_NT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                listNotify.clear();

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                                ) {
                                    Notification notification = dataSnapshot.getValue(Notification.class);
                                    listNotify.add(notification);
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

            databaseRef_listCartOfNT.addListenerForSingleValueEvent(new ValueEventListener() { // lấy list sản phẩm đã đặt hàng
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {

                        String key = dataSnapshot.getRef().getKey(); // lấy id khách
                        databaseRef_listCartOfNT.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                listPurchased.clear();

                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()
                                ) {

                                    String key2 = dataSnapshot1.getRef().getKey(); // lấy id thông báo

                                    databaseRef_listCartOfNT.child(key).child(key2).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                                            for (DataSnapshot dataSnapshot2 : snapshot.getChildren()
                                                 ) {
                                                String key3 = dataSnapshot2.getRef().getKey(); // lấy phân loại

                                                databaseRef_listCartOfNT.child(key).child(key2).child(key3).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                                        for (DataSnapshot dataSnapshot3 : snapshot.getChildren()
                                                             ) {

                                                            Product PD = dataSnapshot3.getValue(Product.class);
                                                            listPurchased.add(PD);
                                                            MainActivity.setNumberBadge(listPurchased.size());

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
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


    }

    public ArrayList<Notification> getListNotify() {
        return listNotify;
    }

    public ArrayList<Product> getListPurchased() {
        return listPurchased;
    }

    public boolean updateNT(int idGuest, Notification notification){

        databaseRef_NT.child(String.valueOf(idGuest)).child(String.valueOf(notification.getIdNT())).setValue(notification);
        return true;
    }

}
