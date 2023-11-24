package com.example.plantshop.firebase;

import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DAO_Notify {

    private DatabaseReference databaseRef_NT, databaseRef_listCartOfNT;
    private ArrayList<Notification> listNotify;
    private ArrayList<Product> listPurchased;

    public DAO_Notify(){

        if(MainActivity.getID > 0){

            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification").child(String.valueOf(MainActivity.getID));
            databaseRef_listCartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product").child(MainActivity.getID + "/");
        }
    }

}
