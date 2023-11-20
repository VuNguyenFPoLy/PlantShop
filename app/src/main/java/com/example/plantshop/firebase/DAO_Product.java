package com.example.plantshop.firebase;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.plantshop.fragment.Fragment_Product;
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
    private boolean check = false;


    public DAO_Product() {

        if (Fragment_Product.key.equals("Xem thêm cây trồng")) {
            databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Cây trồng");
            storageRef = FirebaseStorage.getInstance().getReference("Product").child("Cây trồng");
        } else if (Fragment_Product.key.equals("Xem thêm chậu cây")) {
            databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Chậu cây");
            storageRef = FirebaseStorage.getInstance().getReference("Product").child("Chậu cây");
        } else if (Fragment_Product.key.equals("Xem thêm dụng cụ")) {
            databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Dụng cụ");
            storageRef = FirebaseStorage.getInstance().getReference("Product").child("Dụng cụ");
        }

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


    public static ArrayList<Product> getListPlant() {
        ArrayList<Product> listPlant = new ArrayList<>();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Cây trồng");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listPlant.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listPlant;
    }

    public static ArrayList<Product> getListPots(){
        ArrayList<Product> listPots = new ArrayList<>();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Chậu cây");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listPots.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listPots;
    }

    public static ArrayList<Product> getListTools(){
        ArrayList<Product> listTools = new ArrayList<>();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Dụng cụ");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listTools.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listTools;
    }

    public static boolean deletePlant(int id) {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Cây trồng");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Product").child("Cây trồng");

        databaseRef.child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                storageRef.child(String.valueOf(id)).delete();
            }
        });
        return true;
    }

    public static boolean deletePots(int id) {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Chậu cây");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Product").child("Chậu cây");

        databaseRef.child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                storageRef.child(String.valueOf(id)).delete();
            }
        });
        return true;
    }

    public static boolean deleteTools(int id) {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product").child("Dụng cụ");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Product").child("Dụng cụ");

        databaseRef.child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                storageRef.child(String.valueOf(id)).delete();
            }
        });
        return true;
    }

    public boolean pushProduct(Product product, Uri uri) {

        boolean check = false;

        if (listProduct.size() > 0) { // Kiểm tra trùng lặp sản phẩm
            for (int i = 0; i < listProduct.size(); i++) {
                if (product.getTenSanPham().equals(listProduct.get(i).getTenSanPham())) {
                    check = true;
                    result = false;
                    break;
                }
            }
        }

        if (!check) {
            if (uri != null) {
                result = true;
                storageRef.child(String.valueOf(getID())).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUri = uriTask.getResult();
                                String url = downloadUri.toString();

                                if (product.getIdSanPham() == -1) {
                                    product.setIdSanPham(getID());
                                }
                                if (product.getUrl_Img() == null) {
                                    product.setUrl_Img(url);

                                }
                                databaseRef.child(String.valueOf(getID())).setValue(product);

                            }
                        });
                    }
                });
            }
        }

        return result;
    }

    public boolean updateProduct(Product product){
        databaseRef.child(String.valueOf(product.getIdSanPham())).setValue(product);
        return true;
    }

    public int getID() {
        int id = 0;

        if (listProduct.size() > 0) {
            id = listProduct.get(listProduct.size() - 1).getIdSanPham() + 1;
        }

        return id;
    }
}
