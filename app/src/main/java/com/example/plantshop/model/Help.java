package com.example.plantshop.model;

import android.widget.ImageView;

public class Help {
    private int idHelp;
    private String tv_Question, tv_Content;

    public Help() {
    }

    public Help(int idHelp, String tv_Question, String tv_Content) {
        this.idHelp = idHelp;
        this.tv_Question = tv_Question;
        this.tv_Content = tv_Content;
    }

    public int getIdHelp() {
        return idHelp;
    }

    public void setIdHelp(int idHelp) {
        this.idHelp = idHelp;
    }

    public String getTv_Question() {
        return tv_Question;
    }

    public void setTv_Question(String tv_Question) {
        this.tv_Question = tv_Question;
    }

    public String getTv_Content() {
        return tv_Content;
    }

    public void setTv_Content(String tv_Content) {
        this.tv_Content = tv_Content;
    }
}