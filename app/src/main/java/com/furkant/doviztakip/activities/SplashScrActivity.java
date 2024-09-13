package com.furkant.doviztakip.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.furkant.doviztakip.R;
import com.furkant.doviztakip.fragment.CurrentExchangeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.widget.TextView;

public class SplashScrActivity extends AppCompatActivity {

    private ImageView logoSplash, logoWhite;
    private Animation anim1, anim2, anim3;
    private FirebaseAuth firebaseAuth;
    private TextView chmaraTech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_scr);
        init();


        logoSplash.startAnimation(anim1);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoSplash.startAnimation(anim2);
                logoSplash.setVisibility(View.GONE);

                logoWhite.startAnimation(anim3);
                chmaraTech.startAnimation(anim3);
                anim3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        logoWhite.setVisibility(View.VISIBLE);
                        chmaraTech.setVisibility(View.VISIBLE);

                        finish();
                        firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        SharedPreferences sharedPreferences = getSharedPreferences("introfirst", 0);
                        Boolean firsttime = sharedPreferences.getBoolean("firsttime",true);
                        if(firsttime){
                            firsttime=false;
                            sharedPreferences.edit().putBoolean("firsttime", firsttime).apply();
                            Intent i =new Intent(SplashScrActivity.this,IntroActivity.class);
                            finish();
                            startActivity(i);
                        }
                        //if user is logined then MainActivity else loginactivity
                        else if(user == null){
                            startActivity(new Intent(SplashScrActivity.this,LoginActivity.class));
                        }
                        else
                            startActivity(new Intent(SplashScrActivity.this,MainActivity.class));


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }


    private void init(){

        logoSplash = findViewById(R.id.ivLogoSplash);
        logoWhite = findViewById(R.id.ivLogoWhite);
        chmaraTech = findViewById(R.id.ivCHTtext);
        anim1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout);
        anim3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein);
    }


}
