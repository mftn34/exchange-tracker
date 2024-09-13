package com.furkant.doviztakip.activities;

import com.furkant.doviztakip.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons

        //addSlide(secondFragment);
        //addSlide(thirdFragment);
        //addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.  blue
        addSlide(AppIntroFragment.newInstance("ALIM & SATIM KAYDI", "Döviz takibi ile güncel kurlar üstünden dilediğiniz gibi alım & satım işlemlerinizin kayıtlarını tutabilirsiniz !", R.drawable.den, Color.parseColor("#07A9F9")));
        addSlide(AppIntroFragment.newInstance("GRAFİK ANALİZİ & KAR, ZARAR DURUMU", "Döviz takibi ile döviz değerlerinin belirli zaman aralıklarındaki değer grafiklerini analiz edebilir, yatırım stratejine yön verebilirsin !", R.drawable.den2, Color.parseColor("#07A9F9")));
        addSlide(AppIntroFragment.newInstance("Döviz takibi uygulaması güvende hissettirir !", "Hemen Döviz takibi uygulamasına kayıt ol ! Stratejine yön ver,  Yatırımlarını geliştir, Geleceğe parlak bak ! ", R.drawable.logo, Color.parseColor("#07A9F9")));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.TRANSPARENT);
       setSeparatorColor(Color.parseColor("#07A9F9"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.

        startActivity(new Intent(this,RegistrationActivity.class));
        finish();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(this,RegistrationActivity.class));
        finish();

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.

    }
}
