package com.furkant.doviztakip.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkant.doviztakip.R;
import com.furkant.doviztakip.model.ExchangeModel;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<ExchangeModel> exchangeModelList;


    public RecyclerViewAdapter(ArrayList<ExchangeModel> exchangeModelList) {
        this.exchangeModelList = exchangeModelList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_rows, parent, false);

        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        if (exchangeModelList != null && exchangeModelList.size() > 0) {
            if(position < (exchangeModelList.size()-2)) // why-2 -> nulll data and Since the first element is of type String i.e. "Update_Date"
                holder.bind(exchangeModelList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return exchangeModelList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView text_buying_price;
        TextView text_selling_price;
        TextView text_change_rate;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(ExchangeModel exchangeModel) {
            textName = itemView.findViewById(R.id.text_name);
            text_buying_price = itemView.findViewById(R.id.text_buying_price);
            text_selling_price = itemView.findViewById(R.id.text_selling_price);
            text_change_rate = itemView.findViewById(R.id.text_change_rate);
            //status =itemView.findViewById(R.id.btnStatus);
            // one times screen table
            if(exchangeModel!= null){
                if (exchangeModel.type != null && !exchangeModel.type.isEmpty() && exchangeModel.type.length() > 0 &&
                        exchangeModel.change != null && !exchangeModel.change.isEmpty() && exchangeModel.change.length() > 0 &&
                        exchangeModel.buying != null && !exchangeModel.buying.isEmpty() && exchangeModel.buying.length() > 0 &&
                        exchangeModel.selling != null && !exchangeModel.selling.isEmpty() && exchangeModel.selling.length() > 0) {
                    textName.setText(exchangeModel.type);
                    text_buying_price.setText(exchangeModel.buying);
                    text_selling_price.setText(exchangeModel.selling);
                    text_change_rate.setText(exchangeModel.change);

                    if(Double.parseDouble(exchangeModel.change) >0){
                      text_change_rate.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        text_change_rate.setBackgroundColor(Color.RED);

                    }
                }
            }


        }


    }
}
