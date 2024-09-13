
package com.furkant.doviztakip.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.furkant.doviztakip.R;
import com.furkant.doviztakip.model.UserProfileModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView logo, joinus;
    private AutoCompleteTextView username, email, password;
    private Button signup;
    private TextView signin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_registration);

        initializeGUI();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });


        password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    register();
                    return true;
                }
                return false;
            }
        });

    }


    private void initializeGUI(){

        logo = findViewById(R.id.ivRegLogo);
        //joinus = findViewById(R.id.ivJoinUs);
        username = findViewById(R.id.atvUsernameReg);
        email =  findViewById(R.id.atvEmailReg);
        password = findViewById(R.id.atvPasswordReg);
        signin = findViewById(R.id.tvSignIn);
        signup = findViewById(R.id.btnSignUp);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(final String inputName, final String inputPw, String inputEmail) {

        progressDialog.setMessage(getString(R.string.verificating));
        progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(inputEmail,inputPw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserData(inputName, inputPw);
                        Toast.makeText(RegistrationActivity.this,getString(R.string.signup_success),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }
                    else{
                        if (password.length() < 6) {
                            Toast.makeText(RegistrationActivity.this,getString(R.string.pass_length_err),Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, getString(R.string.email_exists),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

    }


    private void sendUserData(String username, String password){

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference users = firebaseDatabase.getReference("users");
        UserProfileModel user = new UserProfileModel(username, password);
        users.push().setValue(user);

    }

    private boolean validateInput(String inName, String inPw, String inEmail){

        if(inName.isEmpty()){
            username.setError(getString(R.string.userNameIsEmpty));
            return false;
        }
        if(inPw.isEmpty()){
            password.setError(getString(R.string.passIsEmpty));
            return false;
        }
        if(inEmail.isEmpty()){
            email.setError(getString(R.string.emailIsEmpty));
            return false;
        }

        return true;
    }
    @Override
    protected void onStop() {
        // burada firebase close işlemi yapılacak.
        //Log.i("ws",("hacı dayı dur dur aq"));
//        System.out.println("++++++++++++++++++++++++++++++++++++dur aq dur");
//        if(FirebaseDatabase.getInstance()!=null)
//        {
//            FirebaseDatabase.getInstance().goOffline();
//        }

        super.onStop();
    }

    /**
     * register customer
     */
    public void register(){
        final String inputName = username.getText().toString().trim();
        final String inputPw = password.getText().toString().trim();
        final String inputEmail = email.getText().toString().trim();

        if(validateInput(inputName, inputPw, inputEmail))
            registerUser(inputName, inputPw, inputEmail);
    }


}
