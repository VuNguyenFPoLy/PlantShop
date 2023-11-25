package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.model.Product;
import com.example.plantshop.model.Step;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO_Step {

    private DatabaseReference databaseRef;
    private ArrayList<Step> listStep;

    public DAO_Step(int id){

        listStep = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("Hanbook").child(String.valueOf(id)).child("Step");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                     ) {
                    Step step = dataSnapshot.getValue(Step.class);
                    listStep.add(step);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean pushStep(Step step){

        databaseRef.child(String.valueOf(step.getIdStep())).setValue(step);
        return true;
    }

    public boolean deleteStep(int idStep){
        databaseRef.child(String.valueOf(idStep)).removeValue();
        return true;
    }




    public ArrayList<Step> getListStep(){
        return listStep;
    }

    public int getNewId(){
        int id = 0;

            if(listStep.size() > 0){
                id = listStep.get(listStep.size() - 1).getIdStep() + 1;
            }

        return id;
    }
}
