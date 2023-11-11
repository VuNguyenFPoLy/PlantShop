package com.example.plantshop.model;

public class Product {
    private int idSanPham, soLuong;
    private String tenSanPham, loaiSanPham, theLoaiSanPham,
            kichCo, xuatXu, moTa, url_Img;
    private double giaTien;

    public Product() {
    }

    public Product(int idSanPham, String tenSanPham, String loaiSanPham, String theLoaiSanPham, String kichCo, String xuatXu, int soLuong, String moTa, double giaTien, String url_Img) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.kichCo = kichCo;
        this.xuatXu = xuatXu;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.theLoaiSanPham = theLoaiSanPham;
        this.url_Img = url_Img;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public String getTheLoaiSanPham() {
        return theLoaiSanPham;
    }

    public void setTheLoaiSanPham(String theLoaiSanPham) {
        this.theLoaiSanPham = theLoaiSanPham;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public String getUrl_Img() {
        return url_Img;
    }

    public void setUrl_Img(String url_Img) {
        this.url_Img = url_Img;
    }
}
