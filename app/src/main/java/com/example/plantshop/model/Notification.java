package com.example.plantshop.model;

public class Notification {

    private int idNT, idGuest;
    private String dateOder, urlImage;
    private boolean check_COD, check_GHN, check_Pay, status;
    private double sumPrice;


    public Notification() {
    }

    public Notification(int idNT, int idGuest, String dateOder, boolean check_COD, boolean check_GHN, boolean check_Pay, boolean status, double sumPrice, String urlImage) {
        this.idNT = idNT;
        this.idGuest = idGuest;
        this.dateOder = dateOder;
        this.check_COD = check_COD;
        this.check_GHN = check_GHN;
        this.check_Pay = check_Pay;
        this.status = status;
        this.sumPrice = sumPrice;
        this.urlImage = urlImage;
    }

    public int getIdNT() {
        return idNT;
    }

    public void setIdNT(int idNT) {
        this.idNT = idNT;
    }

    public int getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(int idGuest) {
        this.idGuest = idGuest;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDateOder() {
        return dateOder;
    }

    public void setDateOder(String dateOder) {
        this.dateOder = dateOder;
    }

    public boolean isCheck_COD() {
        return check_COD;
    }

    public void setCheck_COD(boolean check_COD) {
        this.check_COD = check_COD;
    }

    public boolean isCheck_GHN() {
        return check_GHN;
    }

    public void setCheck_GHN(boolean check_GHN) {
        this.check_GHN = check_GHN;
    }

    public boolean isCheck_Pay() {
        return check_Pay;
    }

    public void setCheck_Pay(boolean check_Pay) {
        this.check_Pay = check_Pay;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
