package com.example.plantshop.model;

public class Stage {

    private int idStage;
    private int stage;
    private String stageName;
    private String stageContent;

    public Stage() {
    }

    public Stage(int idStage, int stage, String stageName, String stageContent) {
        this.idStage = idStage;
        this.stage = stage;
        this.stageName = stageName;
        this.stageContent = stageContent;
    }


    public int getIdStage() {
        return idStage;
    }

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageContent() {
        return stageContent;
    }

    public void setStageContent(String stageContent) {
        this.stageContent = stageContent;
    }
}
