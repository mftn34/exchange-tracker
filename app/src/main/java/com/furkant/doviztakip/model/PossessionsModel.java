package com.furkant.doviztakip.model;

public class PossessionsModel extends MoneyModel {
    Double maliyet;
    Double miktar;
    Double deger;

    public Double getMaliyet() {
        return maliyet;
    }

    public void setMaliyet(Double maliyet) {
        this.maliyet = maliyet;
    }

    public Double getMiktar() {
        return miktar;
    }

    public void setMiktar(Double miktar) {
        this.miktar = miktar;
    }

    public Double getDeger() {
        return deger;
    }

    public void setDeger(Double deger) {
        this.deger = deger;
    }
}
