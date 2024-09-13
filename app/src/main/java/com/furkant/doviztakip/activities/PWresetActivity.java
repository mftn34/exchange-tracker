package com.furkant.doviztakip.activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.furkant.doviztakip.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PWresetActivity extends AppCompatActivity {

    private ImageView ivLogo,ivPWreset;
    private TextView tvInfo, tvSignin;
    private AutoCompleteTextView atvEmail;
    private Button btnReset;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwreset);
        initializeGUI();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pwReset();

            }
        });


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PWresetActivity.this,LoginActivity.class));
            }
        });


        atvEmail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    pwReset();
                    return true;
                }
                return false;
            }
        });


    }


    private void initializeGUI(){

        ivLogo = findViewById(R.id.ivLogLogo);
        //ivPWreset = findViewById(R.id.ivPassReset);
        tvInfo = findViewById(R.id.tvPWinfo);
        tvSignin = findViewById(R.id.tvGoBack);
        atvEmail = findViewById(R.id.atvEmailRes);
        btnReset = findViewById(R.id.btnReset);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void pwReset(){

        String email = atvEmail.getText().toString();
        if (email.isEmpty()) {
            atvEmail.setError(getString(R.string.emailcheck),null);
        }
        else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(PWresetActivity.this, getString(R.string.send_email_success), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(PWresetActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(PWresetActivity.this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
