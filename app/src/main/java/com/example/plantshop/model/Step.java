package com.example.plantshop.model;

public class Step {

    private int idStep;
    private int step;
    private int idProduct;
    private String StepName;
    private String StepContent;

    public Step() {
    }

    public Step(int idStep, int step, int idProduct, String stepName, String stepContent) {
        this.idStep = idStep;
        this.step = step;
        this.idProduct = idProduct;
        StepName = stepName;
        StepContent = stepContent;
    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public String getStepContent() {
        return StepContent;
    }

    public void setStepContent(String stepContent) {
        StepContent = stepContent;
    }
}
