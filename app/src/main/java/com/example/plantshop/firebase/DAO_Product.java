package com.example.plantshop.firebase;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Account;
import com.example.plantshop.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DAO_Product {
    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private ArrayList<Product> listProduct;
    boolean result = false;

    public DAO_Product() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Product");
        storageRef = FirebaseStorage.getInstance().getReference("Product");
        listProduct = new ArrayList<>();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listProduct.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean pushProduct(Product product, Uri uri){

        boolean check = false;

        if (listProduct.size() > 0) { // Kiểm tra trùng lặp sản phẩm
            for (int i = 0; i < listProduct.size(); i++) {
                if (product.getTenSanPham().equals(listProduct.get(i).getTenSanPham())) {
                    check = true;
                    break;
                }
            }
        }

        if(!check){

            if(uri != null){
                result = true;
                storageRef.child(String.valueOf(getID())).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful());
                                Uri downloadUri = uriTask.getResult();
                                String url = downloadUri.toString();

                                product.setIdSanPham(getID());
                                product.setUrl_Img(url);
                                databaseRef.child(String.valueOf(getID())).setValue(product);

                            }
                        });

                    }
                });
            }
        }

        return result;
    }

    public int getID() {
        int id = 0;

        if (listProduct.size() > 0) {
            id = listProduct.get(listProduct.size() - 1).getIdSanPham() + 1;
        }

        return id;
    }
}