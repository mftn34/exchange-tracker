package com.furkant.doviztakip.fragment;

import com.furkant.doviztakip.R;
import com.furkant.doviztakip.activities.MainActivity;
import com.furkant.doviztakip.adapter.RecyclerViewAdapter;
import com.furkant.doviztakip.model.ExchangeModel;
import com.furkant.doviztakip.model.MoneyModel;
import com.furkant.doviztakip.service.IExhangeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class CurrentExchangeFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<ExchangeModel> resultList = new ArrayList<>();
    public static ArrayList<ExchangeModel> moneyTypeList = new ArrayList<>();
    private Boolean isSorted=false;

    private String BASE_URL = "https://finans.truncgil.com/";
    Retrofit retrofit;

    public CurrentExchangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_exchange, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_exchange, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.currentExhangePageTitle);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        GetData();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (CurrentExchangeFragment.this.isVisible()) {
                    GetData();
                }
            }
        }, 25000);
    }

    protected void GetData() {

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final IExhangeService exchangeService = retrofit.create(IExhangeService.class);
        Call<HashMap<String, Object>> call = exchangeService.getData();
        call.enqueue(new Callback<HashMap<String, Object>>() {

            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                HashMap<String, Object> responseModel = response.body();
                HashMap<String, Object> responseModelSpecialSort = new HashMap<>();

                // Iterate all the currencies
                for (Map.Entry<String, Object> entry : responseModel.entrySet())
                    if (entry.getValue() instanceof LinkedTreeMap) { // Since the first element is of type String i.e. "Update_Date"

                        //set the response
                        if(responseModel!= null){

                            ExchangeModel doviz = new Gson().fromJson(new Gson().toJson(((LinkedTreeMap<String, Object>) entry.getValue())), ExchangeModel.class);
                            ExchangeModel tempModel = new ExchangeModel();

                            if(entry.getKey() != null && !entry.getKey().isEmpty() && entry.getKey().length() > 0 &&
                                    doviz.buying != null && !doviz.buying.isEmpty() && doviz.buying.length() > 0 &&
                                    doviz.selling != null && !doviz.selling.isEmpty() && doviz.selling.length() > 0 &&
                                    doviz.change != null && !doviz.change.isEmpty() && doviz.change.length() > 0)
                            {
                                tempModel.type = entry.getKey();
                                tempModel.buying = doviz.buying;
                                tempModel.selling = doviz.selling;
                                tempModel.change = doviz.change;
                                if(tempModel.type.equals("USD"))
                                    tempModel.sortOrder =1;
                                else if (tempModel.type.equals("EUR"))
                                    tempModel.sortOrder =2;
                                else if (tempModel.type.equals("GBP"))
                                    tempModel.sortOrder =3;
                                else if (tempModel.type.equals("ONS"))
                                    tempModel.sortOrder =4;
                                else
                                    tempModel.sortOrder =99;

                                resultList.add(tempModel);
                            }
                        }
                    }
                // spesific sort area
                    if(!isSorted){
                        Collections.sort(resultList, new Comparator<ExchangeModel>() {
                            @Override public int compare(ExchangeModel p1, ExchangeModel p2) {
                                return p1.sortOrder - p2.sortOrder; // Ascending
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewAdapter = new RecyclerViewAdapter(resultList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        moneyTypeList = resultList;
                        resultList = new ArrayList<>();
                        isSorted=true;
                    }

                }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
            }
        });


    }
}

