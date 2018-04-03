package com.example.gokhan.gezirehberim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecimSayfasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secim_sayfasi);
        android.support.v7.app.ActionBar ab=getSupportActionBar();
        ab.hide();

    }
    public void kesfet(View view){
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
    public void yeniYer(View v)
    {
        Intent i=new Intent(getApplicationContext(),UploadClass.class);
        startActivity(i);
    }
}
