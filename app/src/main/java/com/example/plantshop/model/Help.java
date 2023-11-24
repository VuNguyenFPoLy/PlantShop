package com.example.plantshop.model;

import android.widget.ImageView;

public class Help {
    private int idHelp;
    private ImageView imgShow;
    private String tv_Question;

    public Help() {
    }

    public Help(int idHelp, ImageView imgShow, String tv_Question) {
        this.idHelp = idHelp;
        this.imgShow = imgShow;
        this.tv_Question = tv_Question;
    }

    public int getIdHelp() {
        return idHelp;
    }

    public void setIdHelp(int idHelp) {
        this.idHelp = idHelp;
    }

    public ImageView getImgShow() {
        return imgShow;
    }

    public void setImgShow(ImageView imgShow) {
        this.imgShow = imgShow;
    }

    public String getTv_Question() {
        return tv_Question;
    }

    public void setTv_Question(String tv_Question) {
        this.tv_Question = tv_Question;
    }
}