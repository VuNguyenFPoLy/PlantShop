package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO {
    private DatabaseReference databaseRef;
    private ArrayList<Account> listAccount;

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

    public ArrayList<Account> getListAccount(){
        ArrayList<Account> list = new ArrayList<>();
        list = listAccount;
        return list;
    }

    public boolean setUser(String userName, String passWord) {
        Account account = new Account();
        int id = getID();
        if (!userName.isEmpty() && !passWord.isEmpty()) {
            if (id >= 0) {
                account.setIdAcount(id + 1);
            } else {
                account.setIdAcount(0);
            }
            account.setUserName(userName);
            account.setPassWord(passWord);
            databaseRef.child(String.valueOf(account.getIdAcount())).setValue(account);
        }
        return true;
    }


    public int getID() {
        int id = -1;
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

        if (listAccount.size() > 0) {
            id = listAccount.get(listAccount.size() - 1).getIdAcount();
        }

        return id;
    }


}
