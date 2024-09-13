package com.furkant.doviztakip.model;

public class FirebaseDataModel extends MoneyModel {

    String type;
    Double totalAmount;
    Double rate;
    Double buyingPrice;

    @Override
    public Double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public Double getRate() {
        return rate;
    }

    @Override
    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public Double getBuyingPrice() {
        return buyingPrice;
    }

    @Override
    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
