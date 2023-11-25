package com.example.plantshop.model;

import android.widget.ImageView;

public class Help {
    private ImageView imgShow;
    private String tv_Question, tv_Content;

    public Help() {
    }

    public Help(ImageView imgShow, String tv_Question, String tv_Content) {
        this.imgShow = imgShow;
        this.tv_Question = tv_Question;
        this.tv_Content = tv_Content;
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

    public String getTv_Reply() {
        return tv_Content;
    }

    public void setTv_Reply(String tv_Reply) {
        this.tv_Content = tv_Content;
    }
}