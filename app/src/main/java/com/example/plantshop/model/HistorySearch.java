package com.example.plantshop.model;

public class HistorySearch {
    private int idHistory;
    private String nameSearch;

    public HistorySearch() {
    }

    public HistorySearch(int idHistory, String nameSearch) {
        this.idHistory = idHistory;
        this.nameSearch = nameSearch;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

}
