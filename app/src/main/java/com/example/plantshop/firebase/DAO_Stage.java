package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Stage;
import com.example.plantshop.model.Step;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO_Stage {
    private DatabaseReference databaseRef;
    private ArrayList<Stage> listStage;

    public DAO_Stage(int id){

        listStage = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("Hanbook").child(String.valueOf(id)).child("Stage");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Stage stage = dataSnapshot.getValue(Stage.class);
                    listStage.add(stage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public boolean pushStage(Stage stage){

        databaseRef.child(String.valueOf(stage.getIdStage())).setValue(stage);
        return true;
    }

    public boolean deleteStage(int idStage){
        databaseRef.child(String.valueOf(idStage)).removeValue();
        return true;
    }

    public ArrayList<Stage> getListStage(){
        return listStage;
    }

    public int getNewId(){
        int id = 0;

        if(listStage.size() > 0){
            id = listStage.get(listStage.size() - 1).getIdStage() + 1;
        }

        return id;
    }
}
