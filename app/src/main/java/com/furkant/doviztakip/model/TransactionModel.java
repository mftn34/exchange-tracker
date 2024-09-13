package com.furkant.doviztakip.model;

public class TransactionModel {

    String transactionType;
    String moneyType;
    String transactionDate;
    Double quantity; // miktar
    Double cost; //ALIM İSLEMİ İCİN..
    Double sellAmount; //SATIM İŞLEMİ İCİN..

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Double sellAmount) {
        this.sellAmount = sellAmount;
    }
}
