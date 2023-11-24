package com.example.plantshop.firebase;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Account;
import com.example.plantshop.model.Guest;
import com.example.plantshop.model.HistorySearch;
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
import java.util.Calendar;
import java.util.List;

public class DAO {
    private DatabaseReference databaseRef_AC, databaseRef_Guest;
    private StorageReference storageRef_Guest;
    private ArrayList<Account> listAccount;
    private ArrayList<Guest> listGuest;

    boolean result = false;

    public DAO() {

        databaseRef_AC = FirebaseDatabase.getInstance().getReference("Account");
        databaseRef_Guest = FirebaseDatabase.getInstance().getReference("Guest");
        storageRef_Guest = FirebaseStorage.getInstance().getReference("Guest");

        listAccount = new ArrayList<>();
        listGuest = new ArrayList<>();

        databaseRef_AC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Account account = dataSnapshot.getValue(Account.class);
                    listAccount.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseRef_Guest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Guest guest = dataSnapshot.getValue(Guest.class);
                    listGuest.add(guest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public ArrayList<Account> getListAccount() {
        return listAccount;
    }

    public ArrayList<Guest> getListGuest() {
        return listGuest;
    }

    public static void setIMG(Uri uri) {

        DatabaseReference databaseRef_G = FirebaseDatabase.getInstance().getReference("Guest");
        StorageReference upIMG = FirebaseStorage.getInstance().getReference("Guest");
        upIMG.child(String.valueOf(MainActivity.getID)).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        String url_IMG = downloadUri.toString();

                        Guest guest = MainActivity.guest;
                        guest.setUrl_img(url_IMG);
                        databaseRef_G.child(String.valueOf(guest.getIdGuest())).setValue(guest);
                    }
                });
            }
        });

    }

    public boolean setUser(String userName, String passWord) {

        Account account = new Account();
        Guest guest = new Guest();
        boolean check = false;

        if (listAccount.size() > 0) { // Kiểm tra trùng lặp tài khoản
            for (int i = 0; i < listAccount.size(); i++) {
                if (userName.equals(listAccount.get(i).getUserName())) {
                    check = true;
                    break;
                }
            }
        }

        if (!check) {
            if (!userName.isEmpty() && !passWord.isEmpty()) {

                account.setIdAcount(getID());
                account.setUserName(userName);
                account.setPassWord(passWord);

                guest.setIdGuest(getID());
                guest.setEmail(userName);

                databaseRef_Guest.child(String.valueOf(guest.getIdGuest())).setValue(guest);
                databaseRef_AC.child(String.valueOf(account.getIdAcount())).setValue(account);
                result = true;
            }
        }

        return result;
    }

    public boolean updateAccount(Account account) {
        boolean check = false;

        if (account != null) {
            databaseRef_AC.child(String.valueOf(account.getIdAcount())).setValue(account);
            check = true;
        }

        return check;
    }

    public int getID() {
        int id = 0;

        if (listAccount.size() > 0) {
            id = listAccount.get(listAccount.size() - 1).getIdAcount() + 1;
        }

        return id;
    }

    public boolean checkUser(String userName) {
        boolean check = false;
        if (!userName.isEmpty()) {
            for (Account ac : listAccount
            ) {
                if (userName.equals(ac.getUserName())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
    public boolean checkpass(String passWord) {

        boolean check = false;
        if (!passWord.isEmpty()) {

            for (Account ac : listAccount
            ) {
                if (passWord.equals(ac.getPassWord())) {
                    check = true;
                    break;
                }
            }
        }
        return check;

    }

}
