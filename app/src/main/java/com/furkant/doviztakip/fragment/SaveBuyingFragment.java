package com.furkant.doviztakip.fragment;
import com.furkant.doviztakip.R;

    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.DialogInterface;
    import android.os.Bundle;


    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import android.text.Editable;
    import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;

    import java.util.ArrayList;
    import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.furkant.doviztakip.activities.MainActivity;
import com.furkant.doviztakip.adapter.RecyclerViewAdapter;
import com.furkant.doviztakip.model.ExchangeModel;
import com.furkant.doviztakip.model.MoneyModel;
import com.furkant.doviztakip.model.PossessionsModel;
import com.furkant.doviztakip.model.TransactionModel;
import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;



public class SaveBuyingFragment extends Fragment {

    FirebaseDatabase db;
    public static MoneyModel money;
    EditText rate,buyingAmount,totalAmount;
    Spinner spinnerMoneyType;
    Button btn_al;


    public SaveBuyingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_buying, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.buy_add);
        init(view);
        initializeSpinner();

        //press enter
        buyingAmount.setOnKeyListener(new View.OnKeyListener() {
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               // If the event is a key-down event on the "enter" button
              if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  double x =0.0,y=0.0;
                  x=Double.parseDouble(rate.getText().toString());
                  y=Double.parseDouble(buyingAmount.getText().toString());
                  double sonuc = x*y;
                  totalAmount.setText( String.valueOf(sonuc));

                  return true;
               }
                return false;
          }
        });

        // onayla button
        btn_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMoneyInformation(); // for screen binding
                if(money == null)
                    showMessage(R.string.emptyScreenArea);
                else
                {
                    if(money.getBuyingPrice() == null)
                        showMessage(R.string.emptyBuyingPrice);

                    if(money.getRate() == null)
                        showMessage(R.string.emptyRateCurrency);

                    if(money.getType() == null)
                         showMessage(R.string.emptyMoneyType);

                    if(money.getBuyingPrice() != null && money.getType() != null && money.getRate() != null)
                        areYouSureForBuying().show();
                }
            }
        });
    }


    /**
     * initialize page properties
     * @param v
     */
    protected void init(View v){

        db=FirebaseDatabase.getInstance();

        spinnerMoneyType = (Spinner)v.findViewById(R.id.spinner1);
        rate =(EditText) v.findViewById(R.id.tv_birimkur);
        totalAmount=(EditText)v.findViewById(R.id.totalAmount);
        buyingAmount=(EditText)v.findViewById(R.id.buyingAmount);
        btn_al = (Button) v.findViewById(R.id.btn_buy);
    }

    public Dialog areYouSureForBuying(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getMessage(R.string.buyingCurrencyAmount)+ " "+money.getBuyingPrice()+getMessage(R.string.EnterChar)+
                getMessage(R.string.money_type)+ " " + money.getType()+
                getMessage(R.string.EnterChar)+getMessage(R.string.buyingTotalAmount)+" " +money.getTotalAmount()+
                getMessage(R.string.EnterChar)+getMessage(R.string.areYouSureForBuying))
                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showMessage(R.string.confirmProsesMessage);
                        saveTransaction();
                    }
                })

                .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       showMessage(R.string.cancelProsesMessage);
                    }
                });
        return builder.create();
    }

    protected void saveTransaction(){

            String key = money.getType().replaceAll("[^A-Za-z0-9]", "");
            DatabaseReference dbRefKeyli = db.getReference(MainActivity.userId+getMessage(R.string.varliklar)+key);
            dbRefKeyli.setValue(money);
            DatabaseReference dbRefKeyliTL = db.getReference(MainActivity.userId + getMessage(R.string.TLDegeri ));
            dbRefKeyliTL.setValue(money.getTotalAmount());

    }

    /**
     * Spinner Initialize
     */
    private void initializeSpinner(){
        ArrayList<ExchangeModel> allMoneyList = new ArrayList<>();
        if(CurrentExchangeFragment.moneyTypeList!= null){

           allMoneyList = CurrentExchangeFragment.moneyTypeList;
          ArrayList<String> moneyType = new ArrayList<>();
            if(allMoneyList!= null && allMoneyList.size()>0){
                for(int i = 0; i< allMoneyList.size();i++){
                    //System.out.println("-*-*-*-*-*-*-*-*size-*-*-*-*-*-*-*-*:"+allMoneyList.size());
                    if(allMoneyList.get(i).type != null && allMoneyList.get(i).type.length()>0)
                        moneyType.add(allMoneyList.get(i).type);
                }
               ArrayAdapter adapter = new ArrayAdapter(getActivity(),  android.R.layout.simple_spinner_dropdown_item, moneyType);
                spinnerMoneyType.setAdapter(adapter);
            }
        }
    }

    private void setMoneyInformation(){
        double x =0.0,y=0.0;
        x=Double.parseDouble(rate.getText().toString());
        y=Double.parseDouble(buyingAmount.getText().toString());

        money =new MoneyModel();
        money.setType(spinnerMoneyType.getSelectedItem().toString());
        money.setBuyingPrice(Double.parseDouble(buyingAmount.getText().toString()));
        money.setTotalAmount(x*y);
        money.setRate(Double.parseDouble(rate.getText().toString()));

    }
    private void showMessage(int message){
        Snackbar.make(getView(), message, 2000)
                .setAction(R.string.Action, null).show();
    }
    private String getMessage(int stringId){
        return getResources().getString(stringId);
    }
}
