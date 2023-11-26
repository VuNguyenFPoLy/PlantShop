package com.example.plantshop.model;

public class Help {
    private int idHelp;
    private String Question, Content;

    public Help() {
    }

    public Help(int idHelp, String Question, String Content) {
        this.idHelp = idHelp;
        this.Question = Question;
        this.Content = Content;
    }

    public int getIdHelp() {
        return idHelp;
    }

    public void setIdHelp(int idHelp) {
        this.idHelp = idHelp;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        this.Question = question;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}