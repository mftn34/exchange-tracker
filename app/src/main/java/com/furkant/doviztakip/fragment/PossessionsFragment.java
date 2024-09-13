package com.furkant.doviztakip.fragment;
import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.furkant.doviztakip.service.OnGetDataListener;
import com.furkant.doviztakip.R;
import com.furkant.doviztakip.activities.MainActivity;
import com.furkant.doviztakip.model.FirebaseDataModel;
import com.furkant.doviztakip.model.PossessionsModel;
import com.furkant.doviztakip.service.PossesionsDBService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class PossessionsFragment extends Fragment {

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

    public PossessionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_possessions, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableLayout=(TableLayout) view.findViewById(R.id.tl);
        tv_mevcut_tl_miktari=(TextView)view.findViewById(R.id.tv_mevcut_tl_miktari);

        getAllDatas();
        //PossesionsDBService getAllData = new PossesionsDBService();
        //getAllData.getAllDatas(getActivity());

    }

    public void GetAllPossessionsDataByUserId(final OnGetDataListener listener){
        listener.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference readDb =database.getReference(MainActivity.userId+"/Varliklar");
        readDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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


    private void getAllDatas() {
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
                        //System.out.println("dsadsa******->"+allData.size()+ "+++++" +possessions.getDeger());
                        showDatas(allData,possessions);
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



    public void showDatas(ArrayList<FirebaseDataModel>allData,PossessionsModel possessions){

        tv_mevcut_tl_miktari.setText(String.valueOf(possessions.getDeger()+"0000000").substring(0, 7)+" ₺");

        for(int i=0;i<allData.size();i++)
        {
            //if((allData.get(i).()>0&&Varliklarim_Fragment.varliklarim.get(i).getTur().length() > 0 && Varliklarim_Fragment.varliklarim.get(i).getTur() != null)) {
                yeni_satir_olustur(allData.get(i));
                //toplamdeger = varliklarim.get(i).getDeger() + toplamdeger;
                //varlikyok=false;
            //}
        }
    }

    public void yeni_satir_olustur(FirebaseDataModel varlik){

            yeni_satir_init();
            yeni_satir_set_txt(varlik);

            TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0);
            row.setBackgroundColor(Color.WHITE);
            row.setPadding(10,10,10,10);
            row_params.setMargins(0,5,0,0);
            row.setLayoutParams(row_params);


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
            LinearLayout.LayoutParams tv_tur_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
            tv_tur.setTextSize(17);
            tv_tur.setGravity(Gravity.CENTER);
            tv_tur.setTypeface(Typeface.DEFAULT_BOLD);
            tv_tur.setTextColor(Color.BLACK);
            tv_tur.setLayoutParams(tv_tur_params);

            LinearLayout.LayoutParams imagebutton_tur_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0,0.6f);
            // imagebutton_tur_karzarar.setBackgroundResource(R.drawable.ic_action_name); //DEGİSİCEK..
            //   imagebutton_tur_karzarar.setBackgroundColor(Color.TRANSPARENT);

            imagebutton_tur_karzarar.setLayoutParams(imagebutton_tur_params);




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

            tv_karzarar_txt.setTextSize(17);
            tv_karzarar_txt.setTypeface(Typeface.DEFAULT_BOLD);
            tv_karzarar_txt.setTextColor(Color.BLACK);
            tv_karzarar_txt.setLayoutParams(tv_bilgiler_txt_params);

            LinearLayout.LayoutParams tv_bilgiler_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            tv_miktar.setTextSize(17);
            tv_miktar.setTypeface(Typeface.DEFAULT_BOLD);
            tv_miktar.setTextColor(Color.BLACK);
            tv_miktar.setLayoutParams(tv_bilgiler_params);

            tv_satis.setTextSize(17);
            tv_satis.setTypeface(Typeface.DEFAULT_BOLD);
            tv_satis.setTextColor(Color.BLACK);
            tv_satis.setLayoutParams(tv_bilgiler_params);


            tv_maliyet.setTextSize(17);
            tv_maliyet.setTypeface(Typeface.DEFAULT_BOLD);
            tv_maliyet.setTextColor(Color.BLACK);
            tv_maliyet.setLayoutParams(tv_bilgiler_params);


            LinearLayout.LayoutParams tv_bilgiler_karzarar_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1f);
            ll_karzarar.setOrientation(LinearLayout.HORIZONTAL);
            ll_karzarar.setLayoutParams(tv_bilgiler_karzarar_params);

            LinearLayout.LayoutParams tv_karzarar_params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,2f);
            tv_karzarar.setTextSize(17);
            tv_karzarar.setTypeface(Typeface.DEFAULT_BOLD);
            tv_karzarar.setTextColor(Color.BLACK);
            tv_karzarar.setLayoutParams(tv_karzarar_params);

            LinearLayout.LayoutParams imageview_karzarar_params = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            if(zarar)
                imageview_karzarar.setBackgroundResource(R.drawable.icon_signal_down);
            else
                imageview_karzarar.setBackgroundResource(R.drawable.icon_signal_up);

            imageview_karzarar.setLayoutParams(imageview_karzarar_params);



            ll_satis.addView(tv_satis_txt);
            ll_satis.addView(tv_satis);

            ll_miktar.addView(tv_miktar_txt);
            ll_miktar.addView(tv_miktar);

            ll_maliyet.addView(tv_maliyet_txt,0);
            ll_maliyet.addView(tv_maliyet,1);

            ll_karzarar.addView(tv_karzarar_txt);
            ll_karzarar.addView(tv_karzarar);
            ll_karzarar.addView(imageview_karzarar);

            ll_bilgiler.addView(ll_miktar);
            ll_bilgiler.addView(ll_maliyet);
            ll_bilgiler.addView(ll_satis);
            ll_bilgiler.addView(ll_karzarar);

            ll_tur.addView(tv_tur);
            //ll_tur.addView(imagebutton_tur_karzarar);

            row.addView(ll_tur);
            row.addView(ll_bilgiler);
            tableLayout.addView(row);
//        sv.addView(tableLayout);

        }

    protected void yeni_satir_init(){

        row=new TableRow(getActivity());
        tv_tur=new TextView(getActivity());
        tv_miktar_txt=new TextView(getActivity());
        tv_miktar=new TextView(getActivity());
        tv_maliyet_txt=new TextView(getActivity());
        tv_maliyet=new TextView(getActivity());
        tv_satis_txt=new TextView(getActivity());
        tv_satis=new TextView(getActivity());
        tv_karzarar_txt=new TextView(getActivity());
        tv_karzarar=new TextView(getActivity());
        ll_bilgiler=new LinearLayout(getActivity());
        ll_tur=new LinearLayout(getActivity());
        ll_karzarar=new LinearLayout(getActivity());
        ll_maliyet=new LinearLayout(getActivity());
        ll_miktar=new LinearLayout(getActivity());
        ll_satis=new LinearLayout(getActivity());
        imagebutton_tur_karzarar=new ImageButton(getActivity());
        imageview_karzarar=new ImageView(getActivity());

    }

    protected void yeni_satir_set_txt(FirebaseDataModel varlik){
        tv_tur.setText(varlik.getType());
        tv_miktar.setText(String.valueOf(varlik.getTotalAmount())+" Adet");
        tv_maliyet.setText(String.valueOf(varlik.getBuyingPrice()+"000000").substring(0,6)+" ₺");

        //  tv_karzarar.setText(String .valueOf((varlik.getMiktar()*varlik.getGuncel_alim_kuru())-varlik.getMaliyet())+" ₺");
        tv_karzarar.setText(String.valueOf(varlik.getTotalAmount()-varlik.getBuyingPrice()+"00000000").substring(0, 8)+"₺");
        tv_satis.setText(String.valueOf(varlik.getTotalAmount()+"00000000").substring(0, 8)+" ₺"); // BUNA Bİ BAK..

        if((varlik.getTotalAmount()-varlik.getBuyingPrice())<0 ) zarar=true;
        else zarar=false;

        tv_miktar_txt.setText("Miktar: ");
        tv_maliyet_txt.setText("Maliyet: ");
        tv_satis_txt.setText("TL Değeri: ");
        tv_karzarar_txt.setText("Kar & Zarar Durumu: ");

    }


}
