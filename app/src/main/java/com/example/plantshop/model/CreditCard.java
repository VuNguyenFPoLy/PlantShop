package com.example.plantshop.model;

public class CreditCard {

    private int idCard;
    private String cardNumber;
    private String cardName;
    private String releaseDate;
    private String protectCard;

    public CreditCard() {
    }

    public CreditCard(int idCard, String cardNumber, String cardName, String releaseDate, String protectCard) {
        this.idCard = idCard;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.releaseDate = releaseDate;
        this.protectCard = protectCard;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getProtectCard() {
        return protectCard;
    }

    public void setProtectCard(String protectCard) {
        this.protectCard = protectCard;
    }
}
