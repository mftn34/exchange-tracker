package com.furkant.doviztakip.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.furkant.doviztakip.R;
import com.furkant.doviztakip.fragment.ApplicationInformationFragment;
import com.furkant.doviztakip.fragment.CurrentExchangeFragment;
import com.furkant.doviztakip.fragment.PossessionsFragment;
import com.furkant.doviztakip.fragment.ProfilFragment;

import com.furkant.doviztakip.fragment.SaveBuyingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    public static FirebaseUser user;
    public static String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        welcomePage(toolbar);
    }

    public void welcomePage(Toolbar toolbar) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


        displaySelectedScreen(R.id.nav_CurrentExchangeScreen); //// GİRİŞ SAYFASI İÇİN
    }

    private void displaySelectedScreen(int id) {
        Fragment fragment = null;

        if (id == R.id.nav_CurrentExchangeScreen) {
            fragment = new CurrentExchangeFragment();
        }
        else if (id == R.id.nav_possesions) {

            Intent possessionsActivity =new Intent(this, PossessionsActivity.class);
            startActivity(possessionsActivity);
            finish();

        }else if (id == R.id.nav_profile) {
            fragment = new ProfilFragment();
        }
        else if (id == R.id.nav_ApplicationInformation) {
            fragment = new ApplicationInformationFragment();
        }
        else if (id == R.id.nav_LogOut) {
            CreateDialogForLogOut();
        }
        else if (id == R.id.nav_Chat) {
            Intent chat =new Intent(this,ChatActivity.class);
            startActivity(chat);
        }
        else if (id == R.id.nav_SaveBuying) {
            fragment=new SaveBuyingFragment();
        }


        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * Oturum kapatma butonu işlemleridir.
     */
    public void CreateDialogForLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.dialog_for_logout)
                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, R.string.toast_logout, Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        Intent returnSıgnIn = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(returnSıgnIn);
                        finish();
                    }
                })
                .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
    }

    /**
     * Bottom navigation code area
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment fragment = null;
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_CurrentExchangeScreen:
                            fragment = new CurrentExchangeFragment();
                            break;
                        case R.id.nav_possesions:
                            fragment = new PossessionsFragment();
                            break;
                        case R.id.nav_profile:
                            fragment = new ProfilFragment();
                            break;
                    }


//                    if(selectedFragment != null)
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                selectedFragment).commit();
                    if (fragment != null) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.content_main, fragment);
                        fragmentTransaction.commit();
                    }

                    return true;
                }
            };






}
