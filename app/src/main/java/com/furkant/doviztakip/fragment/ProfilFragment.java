package com.furkant.doviztakip.fragment;
import com.furkant.doviztakip.R;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.furkant.doviztakip.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilFragment extends Fragment {
    private Button btnChangeEmail, labelbtnChangePass,
            btnChangePass,labelBtnChangeEmail;
    private TextView textViewEmail;
    private EditText  edittextNewEmail, edittextNewPass;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.header_Profile_Information);

        init(view);

        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }
            }
        };

        edittextNewEmail.setVisibility(View.GONE);
        edittextNewPass.setVisibility(View.GONE);
        btnChangeEmail.setVisibility(View.GONE);
        btnChangePass.setVisibility(View.GONE);
        textViewEmail.setText(user.getEmail());
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        labelBtnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anim(edittextNewEmail);
                edittextNewEmail.setVisibility(View.VISIBLE);
                edittextNewPass.setVisibility(View.GONE);
                anim(btnChangeEmail);
                btnChangeEmail.setVisibility(View.VISIBLE);
                btnChangePass.setVisibility(View.GONE);
            }
        });
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !edittextNewEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(edittextNewEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), R.string.message_email_updated, Toast.LENGTH_LONG).show();
                                        auth.signOut();
                                        Intent home = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(home);
                                        getActivity().finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getActivity(), R.string.message_error_update_email, Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (edittextNewEmail.getText().toString().trim().equals("")) {
                    edittextNewEmail.setError(getResources().getString(R.string.message_empty_email_area));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        labelbtnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edittextNewEmail.setVisibility(View.GONE);
                anim(edittextNewPass);
                edittextNewPass.setVisibility(View.VISIBLE);
                btnChangeEmail.setVisibility(View.GONE);
                anim(btnChangePass);
                btnChangePass.setVisibility(View.VISIBLE);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !edittextNewPass.getText().toString().trim().equals("")) {
                    if (edittextNewPass.getText().toString().trim().length() < 6) {
                        edittextNewPass.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(edittextNewPass.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Sifreniz Güncellendi, Lütfen Yeni Sifreniz İle Giriş Yapınız..", Toast.LENGTH_LONG).show();
                                            auth.signOut();
                                            Intent giris = new Intent(getActivity(), LoginActivity.class);
                                            startActivity(giris);
                                            getActivity().finish();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(getActivity(), "Sifre Güncelleme İşlemi Başarısız!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (edittextNewPass.getText().toString().trim().equals("")) {
                    edittextNewPass.setError("Sifrenizi Giriniz..");
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }


    protected void init(View v){
        labelBtnChangeEmail = (Button) v.findViewById(R.id.labelBtnChangeEmail);
        labelbtnChangePass = (Button) v.findViewById(R.id.labelbtnChangePass);
        btnChangeEmail = (Button) v.findViewById(R.id.btnChangeEmail);
        btnChangePass = (Button) v.findViewById(R.id.btnChangePass);
        textViewEmail=(TextView)v.findViewById(R.id.textViewEmail);
        edittextNewEmail = (EditText) v.findViewById(R.id.edittextNewEmail);
        edittextNewPass = (EditText) v.findViewById(R.id.edittextNewPass);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

    }
    protected void anim(View v){
        if(v instanceof EditText) {
            YoYo.with(Techniques.RollIn)
                    .duration(2000)
                    .playOn(v);
        }
        else {
            YoYo.with(Techniques.BounceIn)
                    .duration(2000)
                    .playOn(v);
        }
    }
}
