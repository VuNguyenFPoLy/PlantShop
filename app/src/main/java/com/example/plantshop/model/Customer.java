package com.example.plantshop.model;

public class Customer {
    private int idCustomer;
    private String email;
    private String address;
    private String phoneNumber;
    private String fullName;
    private String url_img;

    public Customer() {
    }

    public Customer(int idCustomer, String email, String address, String phoneNumber, String fullName, String url_img) {
        this.idCustomer = idCustomer;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.url_img = url_img;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
