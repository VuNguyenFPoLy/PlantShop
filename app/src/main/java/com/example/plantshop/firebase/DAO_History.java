package com.example.plantshop.firebase;

import androidx.annotation.NonNull;

import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.HistorySearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DAO_History {

    private DatabaseReference databaseRef_History_Search;
    private ArrayList<HistorySearch> listHis;

    public DAO_History(){
        databaseRef_History_Search = FirebaseDatabase.getInstance().getReference("History").child(String.valueOf(MainActivity.getID));

        listHis = new ArrayList<>();

        databaseRef_History_Search.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    HistorySearch historySearch = dataSnapshot.getValue(HistorySearch.class);
                    listHis.add(historySearch);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<HistorySearch> getListHistory() {
        return listHis;
    }

    public void saveHistory(String nameProduct, int idUser) {
        int index, idH;

        if (listHis.size() > 0) {
            index = listHis.size() - 1;
            idH = listHis.get(index).getIdHistory() + 1;
        } else {
            idH = 0;
        }

        HistorySearch historySearch = new HistorySearch();
        historySearch.setIdHistory(idH);
        historySearch.setNameSearch(nameProduct);
        databaseRef_History_Search.child(String.valueOf(idH)).setValue(historySearch);

    }


}
