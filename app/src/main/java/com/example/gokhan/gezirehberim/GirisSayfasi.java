package com.example.gokhan.gezirehberim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class GirisSayfasi extends AppCompatActivity {
    private static final String TAG ="" ;
    EditText kAdi;
EditText kSifre;
CardView girisyap;
CardView kayitol;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_sayfasi);
        android.support.v7.app.ActionBar ab=getSupportActionBar();
        ab.hide();
        kAdi=(EditText)findViewById(R.id.kullaniciAdi);
        kSifre=(EditText)findViewById(R.id.kullaniciSifre);
        girisyap=(CardView) findViewById(R.id.girisYap);
        kayitol=(CardView) findViewById(R.id.kayitOl);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
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
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void giris_yap(View v){
        String maili=kAdi.getText().toString(); String sifesi=kSifre.getText().toString();
        if (maili.matches("") && sifesi.matches("")){
            Toast.makeText(GirisSayfasi.this,"Lütfen bilgilerinizi giriniz..",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(sifesi.matches("")){
            Toast.makeText(GirisSayfasi.this,"Lütfen şifrenizi giriniz..",Toast.LENGTH_SHORT).show();return;
        }

        else if (maili.matches("")){
            Toast.makeText(GirisSayfasi.this,"Lütfen mail adresinizi giriniz..",Toast.LENGTH_SHORT).show();
            return;
        }


    mAuth.signInWithEmailAndPassword(kAdi.getText().toString(), kSifre.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        Intent i=new Intent(getApplicationContext(),SecimSayfasi.class);
                        startActivity(i);


                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(GirisSayfasi.this, "Kullanıcı adı veya şifre hatalı..",
                                Toast.LENGTH_SHORT).show();

                    }

                    // ...
                }
            });


}
public void kayit_ol(View v){
  /*  mAuth.createUserWithEmailAndPassword(kAdi.getText().toString(), kSifre.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Kullanıcı oluşturuldu");
                        Toast.makeText(GirisSayfasi.this, "Kullanıcı başarıyla oluşturuldu..",
                                Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),SecimSayfasi.class);
                        startActivity(i);


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        if(kSifre.getText().length()<6){
                            Toast.makeText(GirisSayfasi.this, "Şifre en az 6 karakter olmalı.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(GirisSayfasi.this, "Kullanıcı adı hatalı",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


                }
            });*/
    Intent i=new Intent(getApplicationContext(),Kayitol.class);
    startActivity(i);

}


}


