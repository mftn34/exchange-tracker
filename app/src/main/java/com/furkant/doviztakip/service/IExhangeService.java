package com.furkant.doviztakip.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IExhangeService {

    @GET("v4/today.json")
    Call<HashMap<String, Object>> getData();



}
