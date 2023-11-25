package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Help;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO_Help {
    private DatabaseReference databaseRef_Help;
    private ArrayList<Help> listHelp;

    public DAO_Help(){
        databaseRef_Help = FirebaseDatabase.getInstance().getReference("Help");
        listHelp = new ArrayList<>();

        databaseRef_Help.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Help help = dataSnapshot.getValue(Help.class);
                    listHelp.add(help);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            public ArrayList<Help> getListHelp() {
                return listHelp;
            }
        });
    }
}
