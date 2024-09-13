package com.furkant.doviztakip.service;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.furkant.doviztakip.R;
import com.furkant.doviztakip.activities.MainActivity;
import com.furkant.doviztakip.model.FirebaseDataModel;
import com.furkant.doviztakip.model.PossessionsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PossesionsDBService {
    ArrayList<FirebaseDataModel> allData  = new ArrayList<>();;
    PossessionsModel possessions = new PossessionsModel();
    TableLayout tableLayout;
    TextView tv_mevcut_tl_miktari;

    ScrollView sv;
    TableRow row;
    ImageButton imagebutton_tur_karzarar;
    ImageView imageview_karzarar;
    TextView tv_varlik_yok,tv_tur,tv_miktar_txt,tv_miktar,tv_maliyet_txt,tv_maliyet,
            tv_satis_txt,tv_satis,tv_karzarar_txt,tv_karzarar,tv_toplam_bakiye,tv_toplam_bakiye_txt;
    LinearLayout ll_tur,ll_miktar,ll_maliyet,
            ll_satis,ll_karzarar,ll_bilgiler;
    Double toplamdeger;
    Boolean zarar, varlikyok=true;

    public void GetAllPossessionsDataByUserId(final OnGetDataListener listener){
        listener.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference readDb =database.getReference(MainActivity.userId+"/Varliklar");
        readDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(" neyi başaramadınnnn------->"+ dataSnapshot);
                listener.onSuccess(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });

    }
    public void GetPossesionsTLValues(final OnGetDataListener listener) {
        //System.out.println("run count 2 ");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference readTLValues = database.getReference(MainActivity.userId + "/TLDegeri");
        readTLValues.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getAllDatas(FragmentActivity activity) {
        GetAllPossessionsDataByUserId(new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                Iterable<DataSnapshot> keys = data.getChildren();
                for(DataSnapshot key : keys){
                    FirebaseDataModel dataModel = key.getValue(FirebaseDataModel.class);

//                    System.out.println("----->"+dataModel.getBuyingPrice());
//                    System.out.println("----->"+dataModel.getRate());
//                    System.out.println("----->"+dataModel.getType());
//                    System.out.println("----->"+dataModel.getTotalAmount());
                    allData.add(dataModel);
                }
                GetPossesionsTLValues(new OnGetDataListener() {
                    @Override
                    public void onStart() {
                        //DO SOME THING WHEN START GET DATA HERE
                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        //System.out.println("run count 3 ");
                        Long tlValue = new Long((long) data.getValue());

                        if (tlValue != null) {

                            double tlValueResult = tlValue.doubleValue();
                            possessions.setDeger(tlValueResult);
                            //System.out.println("result"+tlValueResult);
                        } else
                            possessions.setDeger(0.0);
                        System.out.println("düzelicez inşallah"+allData.size()+ "+++++" +possessions.getDeger());
                        showDatas(activity,allData,possessions);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        //DO SOME THING WHEN GET DATA FAILED HERE
                    }
                });

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        });
    }



    public void showDatas(FragmentActivity activity, ArrayList<FirebaseDataModel> allData, PossessionsModel possessions){
        System.out.println("baba burada mı hata 1");

        //if(possessions!= null && possessions.getDeger() != null)
          //  tv_mevcut_tl_miktari.setText(String.valueOf(possessions.getDeger()+"0000000").substring(0, 7)+" ₺");

        System.out.println("baba burada mı hata 2");
        for(int i=0;i<allData.size();i++)
        {
            System.out.println("baba burada mı hata "+(3+i));
            //if((allData.get(i).()>0&&Varliklarim_Fragment.varliklarim.get(i).getTur().length() > 0 && Varliklarim_Fragment.varliklarim.get(i).getTur() != null)) {
            yeni_satir_olustur(activity,allData.get(i));
            //toplamdeger = varliklarim.get(i).getDeger() + toplamdeger;
            //varlikyok=false;
            //}
        }
    }

    public void yeni_satir_olustur(FragmentActivity activity,FirebaseDataModel varlik){

        yeni_satir_init(activity);
        System.out.println("yeni satır olustu");
        yeni_satir_set_txt(varlik);

        TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0);
        row.setBackgroundColor(Color.WHITE);
        row.setPadding(10,10,10,10);
        row_params.setMargins(0,5,0,0);
        row.setLayoutParams(row_params);

        System.out.println("yeni satır olustu devam 1");
        TableRow.LayoutParams ll_tur_params = new TableRow.LayoutParams(0,TableRow.LayoutParams.MATCH_PARENT,0.8f);
        ll_tur.setWeightSum(2f);
        ll_tur.setOrientation(LinearLayout.VERTICAL);
        ll_tur.setLayoutParams(ll_tur_params);
        ll_tur.setGravity(Gravity.CENTER);
        //
        //     <LinearLayout 
        //  android:weightSum="2" 
        //    android:layout_weight="1" 
        //     android:orientation="vertical" 
        //   android:layout_width="0dp" 
        //    android:layout_height="match_parent" 
        // >
        System.out.println("yeni satır olustu devam 2");
        LinearLayout.LayoutParams tv_tur_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
        tv_tur.setTextSize(17);
        tv_tur.setGravity(Gravity.CENTER);
        tv_tur.setTypeface(Typeface.DEFAULT_BOLD);
        tv_tur.setTextColor(Color.BLACK);
        tv_tur.setLayoutParams(tv_tur_params);
        System.out.println("yeni satır olustu devam 3");
        LinearLayout.LayoutParams imagebutton_tur_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0,0.6f);
        // imagebutton_tur_karzarar.setBackgroundResource(R.drawable.ic_action_name); //DEGİSİCEK..
        //   imagebutton_tur_karzarar.setBackgroundColor(Color.TRANSPARENT);

        imagebutton_tur_karzarar.setLayoutParams(imagebutton_tur_params);
        System.out.println("yeni satır olustu devam 4");



        TableRow.LayoutParams ll_bilgiler_params = new TableRow.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,2f);
        ll_bilgiler.setOrientation(LinearLayout.VERTICAL);
        ll_bilgiler.setLayoutParams(ll_bilgiler_params);

        LinearLayout.LayoutParams ll_bilgiler_txt_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
        ll_satis.setOrientation(LinearLayout.HORIZONTAL);
        ll_maliyet.setOrientation(LinearLayout.HORIZONTAL);
        ll_miktar.setOrientation(LinearLayout.HORIZONTAL);
        ll_satis.setLayoutParams(ll_bilgiler_txt_params);
        ll_miktar.setLayoutParams(ll_bilgiler_txt_params);
        ll_maliyet.setLayoutParams(ll_bilgiler_txt_params);
        System.out.println("yeni satır olustu devam 5");
        LinearLayout.LayoutParams tv_bilgiler_txt_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        tv_miktar_txt.setTextSize(17);
        tv_miktar_txt.setTypeface(Typeface.DEFAULT_BOLD);
        tv_miktar_txt.setTextColor(Color.BLACK);
        tv_miktar_txt.setLayoutParams(tv_bilgiler_txt_params);

        tv_satis_txt.setTextSize(17);
        tv_satis_txt.setTypeface(Typeface.DEFAULT_BOLD);
        tv_satis_txt.setTextColor(Color.BLACK);
        tv_satis_txt.setLayoutParams(tv_bilgiler_txt_params);

        tv_maliyet_txt.setTextSize(17);
        tv_maliyet_txt.setTypeface(Typeface.DEFAULT_BOLD);
        tv_maliyet_txt.setTextColor(Color.BLACK);
        tv_maliyet_txt.setLayoutParams(tv_bilgiler_txt_params);
        System.out.println("yeni satır olustu devam 6");
        tv_karzarar_txt.setTextSize(17);
        tv_karzarar_txt.setTypeface(Typeface.DEFAULT_BOLD);
        tv_karzarar_txt.setTextColor(Color.BLACK);
        tv_karzarar_txt.setLayoutParams(tv_bilgiler_txt_params);

        LinearLayout.LayoutParams tv_bilgiler_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        tv_miktar.setTextSize(17);
        tv_miktar.setTypeface(Typeface.DEFAULT_BOLD);
        tv_miktar.setTextColor(Color.BLACK);
        tv_miktar.setLayoutParams(tv_bilgiler_params);
        System.out.println("yeni satır olustu devam 7");
        tv_satis.setTextSize(17);
        tv_satis.setTypeface(Typeface.DEFAULT_BOLD);
        tv_satis.setTextColor(Color.BLACK);
        tv_satis.setLayoutParams(tv_bilgiler_params);


        tv_maliyet.setTextSize(17);
        tv_maliyet.setTypeface(Typeface.DEFAULT_BOLD);
        tv_maliyet.setTextColor(Color.BLACK);
        tv_maliyet.setLayoutParams(tv_bilgiler_params);
        System.out.println("yeni satır olustu devam 8");

        LinearLayout.LayoutParams tv_bilgiler_karzarar_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
        ll_karzarar.setOrientation(LinearLayout.HORIZONTAL);
        ll_karzarar.setLayoutParams(tv_bilgiler_karzarar_params);

        LinearLayout.LayoutParams tv_karzarar_params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,2f);
        tv_karzarar.setTextSize(17);
        tv_karzarar.setTypeface(Typeface.DEFAULT_BOLD);
        tv_karzarar.setTextColor(Color.BLACK);
        tv_karzarar.setLayoutParams(tv_karzarar_params);
        System.out.println("yeni satır olustu devam 9");
        LinearLayout.LayoutParams imageview_karzarar_params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
        if(zarar)
            imageview_karzarar.setBackgroundResource(R.drawable.icon_signal_down);
        else
            imageview_karzarar.setBackgroundResource(R.drawable.icon_signal_up);

        imageview_karzarar.setLayoutParams(imageview_karzarar_params);

        System.out.println("yeni satır olustu devam 11 ");

        ll_satis.addView(tv_satis_txt);
        ll_satis.addView(tv_satis);

        ll_miktar.addView(tv_miktar_txt);
        ll_miktar.addView(tv_miktar);
        System.out.println("yeni satır olustu devam 12");
        ll_maliyet.addView(tv_maliyet_txt,0);
        ll_maliyet.addView(tv_maliyet,1);

        ll_karzarar.addView(tv_karzarar_txt);
        ll_karzarar.addView(tv_karzarar);
        ll_karzarar.addView(imageview_karzarar);
        System.out.println("yeni satır olustu devam 13");
        ll_bilgiler.addView(ll_miktar);
        ll_bilgiler.addView(ll_maliyet);
        ll_bilgiler.addView(ll_satis);
        ll_bilgiler.addView(ll_karzarar);
        System.out.println("yeni satır olustu devam 14");
        ll_tur.addView(tv_tur);
        System.out.println("yeni satır olustu devam 15");
        //ll_tur.addView(imagebutton_tur_karzarar);

        row.addView(ll_tur);
        System.out.println("yeni satır olustu devam 16");
        row.addView(ll_bilgiler);
        System.out.println("yeni satır olustu devam 17");
       // tableLayout.addView(row);
        System.out.println("yeni satır olustu devam 18");
//        sv.addView(tableLayout);
        System.out.println("yeni satır olustu devam son bitti");
    }

    protected void yeni_satir_init(FragmentActivity activity){
        System.out.println("hi hi hi hi ******");
        row=new TableRow(activity);
        tv_tur=new TextView(activity);
        tv_miktar_txt=new TextView(activity);
        tv_miktar=new TextView(activity);
        tv_maliyet_txt=new TextView(activity);
        tv_maliyet=new TextView(activity);
        tv_satis_txt=new TextView(activity);
        tv_satis=new TextView(activity);
        tv_karzarar_txt=new TextView(activity);
        tv_karzarar=new TextView(activity);
        ll_bilgiler=new LinearLayout(activity);
        ll_tur=new LinearLayout(activity);
        ll_karzarar=new LinearLayout(activity);
        ll_maliyet=new LinearLayout(activity);
        ll_miktar=new LinearLayout(activity);
        ll_satis=new LinearLayout(activity);
        imagebutton_tur_karzarar=new ImageButton(activity);
        imageview_karzarar=new ImageView(activity);
        System.out.println("2 2 2 hi hi hi hi ******");
    }

    protected void yeni_satir_set_txt(FirebaseDataModel varlik){
        System.out.println("yeni satır set edilmesi");
        if(varlik != null && varlik.getType() != null)
            tv_tur.setText(varlik.getType());

        System.out.println("yeni satır set edilmesi1");

        if(varlik != null && varlik.getTotalAmount() != null)
            tv_miktar.setText(String.valueOf(varlik.getTotalAmount())+" Adet");

        System.out.println("yeni satır set edilmesi2");

        if(varlik != null && varlik.getBuyingPrice() != null)
        tv_maliyet.setText(String.valueOf(varlik.getBuyingPrice()+"000000").substring(0,6)+" ₺");

        System.out.println("yeni satır set edilmesi3");
        //  tv_karzarar.setText(String .valueOf((varlik.getMiktar()*varlik.getGuncel_alim_kuru())-varlik.getMaliyet())+" ₺");
        if(varlik != null &&  varlik.getTotalAmount() != null && varlik.getBuyingPrice() != null)
            tv_karzarar.setText(String.valueOf(varlik.getTotalAmount()-varlik.getBuyingPrice()+"00000000").substring(0, 8)+"₺");

        System.out.println("yeni satır set edilmesi4");
        if(varlik != null &&  varlik.getTotalAmount() != null)
            tv_satis.setText(String.valueOf(varlik.getTotalAmount()+"00000000").substring(0, 8)+" ₺"); // BUNA Bİ BAK..

        System.out.println("yeni satır set edilmesi5");
        if(varlik != null &&  varlik.getTotalAmount() != null && varlik.getBuyingPrice()!= null )
            if((varlik.getTotalAmount()-varlik.getBuyingPrice())<0 ) zarar=true;
            else zarar=false;
        System.out.println("yeni satır set edilmesi7");
        tv_miktar_txt.setText("Miktar: ");
        tv_maliyet_txt.setText("Maliyet: ");
        tv_satis_txt.setText("TL Değeri: ");
        tv_karzarar_txt.setText("Kar & Zarar Durumu: ");

    }
}
