package com.example.plantshop.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Account;
import com.example.plantshop.model.Customer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO {
    private DatabaseReference databaseRef;
    private ArrayList<Account> listAccount;
    boolean result = false;

    public DAO() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Account");
        listAccount = new ArrayList<>();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
    }

    public ArrayList<Account> getListAccount() {
        return listAccount;
    }

    public boolean setUser(String userName, String passWord) {
        Account account = new Account();
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


                databaseRef.child(String.valueOf(account.getIdAcount())).setValue(account);
                result = true;
            }
        }

        return result;
    }

    public boolean updateAccount(Account account){
        boolean check = false;

        if (account != null){
            databaseRef.child(String.valueOf(account.getIdAcount())).setValue(account);
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
                if(userName.equals(ac.getUserName())){
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


}
