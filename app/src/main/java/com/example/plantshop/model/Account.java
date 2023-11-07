package com.example.plantshop.model;

public class Account {
    private int idAcount;
    private String userName;
    private String passWord;

    public Account() {
    }

    public Account(int idAcount, String userName, String passWord) {
        this.idAcount = idAcount;
        this.userName = userName;
        this.passWord = passWord;
    }

    public int getIdAcount() {
        return idAcount;
    }

    public void setIdAcount(int idAcount) {
        this.idAcount = idAcount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
