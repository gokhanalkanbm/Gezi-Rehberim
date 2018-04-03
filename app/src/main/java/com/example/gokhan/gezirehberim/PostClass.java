package com.example.gokhan.gezirehberim;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gokhan on 17.12.2017.
 */

public class PostClass extends ArrayAdapter<String> {
    public ArrayList<String> kullanicimail;
    public ArrayList<String> kullaniciYorumu;
    public  ArrayList<String> kullaniciResim;

    public Activity context;
    public PostClass(ArrayList<String> kullanicimail, ArrayList<String> kullaniciYorumu, ArrayList<String> kullaniciResim, Activity context) {
        super(context, R.layout.kullanici_sayfasi,kullaniciYorumu);
        this.kullanicimail = kullanicimail;
        this.kullaniciYorumu = kullaniciYorumu;
        this.kullaniciResim = kullaniciResim;
        this.context = context;
    }

    @NonNull
    @Override
    public  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View CustomView=layoutInflater.inflate(R.layout.kullanici_sayfasi,null,true);

        TextView kullanicimailtext=(TextView)CustomView.findViewById(R.id.kullanici_adi);
        TextView kulllaniciyorumtext=(TextView)CustomView.findViewById(R.id.kullanici_yorumu);
        ImageView kullaniciresimtext=(ImageView)CustomView.findViewById(R.id.kullanici_resmi);
        kullanicimailtext.setText(kullanicimail.get(position));
        kulllaniciyorumtext.setText(kullaniciYorumu.get(position));
        Picasso.with(context).load(kullaniciResim.get(position)).into(kullaniciresimtext);




        return CustomView;
    }
}
