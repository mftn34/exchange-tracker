package com.furkant.doviztakip.model;
import com.google.gson.annotations.SerializedName;

public class ExchangeModel {

    @SerializedName("Type")
    public String type;

    @SerializedName("Buying")
    public String buying;

    @SerializedName("Selling")
    public String selling;

    @SerializedName("Change")
    public String change;

    public int sortOrder;


/*
"Buying": "11.0348",
        "Type": "Currency",
        "Selling": "11.0501",
        "Change": "-0.57"
 */
}
