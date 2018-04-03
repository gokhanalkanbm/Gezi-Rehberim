package com.example.gokhan.gezirehberim;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

ListView listView;
PostClass adapter;
FirebaseDatabase firebaseDatabase;
DatabaseReference myref;
ArrayList<String> userresimfb=new ArrayList<String>();
ArrayList<String> useryorumfb=new ArrayList<String>();
ArrayList<String> useremailfb=new ArrayList<String>();
SearchView searchView;
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.addpost){
            Intent i=new Intent(getApplicationContext(),UploadClass.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#85c0ca")));
        //android.support.v7.app.ActionBar ab=getSupportActionBar();
        //ab.hide();

        listView =(ListView)findViewById(R.id.listView);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myref=firebaseDatabase.getReference();
adapter=new PostClass(useremailfb,useryorumfb,userresimfb,this);
listView.setAdapter(adapter);
getData();



    }

    protected void getData(){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Gonderi");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String, String> hashMap =(HashMap<String, String>)
                    ds.getValue();
                    useremailfb.add(hashMap.get("email"));
                    useryorumfb.add(hashMap.get("yorum"));
                    userresimfb.add(hashMap.get("downloadurl"));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

}
