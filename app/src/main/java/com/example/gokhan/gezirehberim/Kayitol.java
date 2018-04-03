package com.example.gokhan.gezirehberim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Kayitol extends AppCompatActivity {
    public static final String TAG = "";
    EditText username;
    EditText usermail;
    EditText userpassword;
    CardView hesap;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.hide();

        usermail = (EditText) findViewById(R.id.userMail);
        userpassword = (EditText) findViewById(R.id.userPassword);
        hesap = (CardView) findViewById(R.id.hesapOlustur);
        mauth = FirebaseAuth.getInstance();
        mauthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mauthListener != null) {
            mauth.removeAuthStateListener(mauthListener);
        }
    }

    public void hesap_olustur(View view) {
        String maili=usermail.getText().toString(); String sifesi=userpassword.getText().toString();
        if (maili.matches("") && sifesi.matches("")){
            Toast.makeText(Kayitol.this,"Lütfen bilgilerinizi giriniz..",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(sifesi.matches("")){
            Toast.makeText(Kayitol.this,"Lütfen şifrenizi giriniz..",Toast.LENGTH_SHORT).show();return;
        }

        else if (maili.matches("")){
            Toast.makeText(Kayitol.this,"Lütfen mail adresinizi giriniz..",Toast.LENGTH_SHORT).show();
            return;
        }

        mauth.createUserWithEmailAndPassword(usermail.getText().toString(), userpassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Kullanıcı oluşturuldu");
                            Toast.makeText(Kayitol.this, "Kullanıcı başarıyla oluşturuldu..",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), GirisSayfasi.class);
                            startActivity(i);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if (userpassword.getText().length() < 6) {
                                Toast.makeText(Kayitol.this, "Şifre en az 6 karakter olmalı.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Kayitol.this, "Kullanıcı adı hatalı",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }


                    }
                });
    }
}

