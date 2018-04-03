package com.example.gokhan.gezirehberim;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class UploadClass extends AppCompatActivity {
EditText yorum;
Uri image;
ImageView resim;
Bitmap selectimg;
FirebaseDatabase firedatabase;
DatabaseReference myref;
private StorageReference mStorageRef;
private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener mAuthListener;
CardView cardView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_class);
        android.support.v7.app.ActionBar ab=getSupportActionBar();
        ab.hide();
        yorum=(EditText)findViewById(R.id.editText);
        cardView=(CardView)findViewById(R.id.cardView);

        resim=(ImageView)findViewById(R.id.imageView);
        firedatabase=FirebaseDatabase.getInstance();
        myref=firedatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();

    }




    public void eklemeYap(View v){
        Toast.makeText(getApplicationContext(),"Gönderiniz yükleniyor lütfen bekleyiniz..",Toast.LENGTH_LONG).show();
        UUID uuidname = UUID.randomUUID();
        String imagesname ="images/"+uuidname.toString()+".jpg";
     StorageReference storageReference = mStorageRef.child(imagesname);
        storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("jjj")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadurl= taskSnapshot.getDownloadUrl().toString();
                String yorumun= yorum.getText().toString();
                FirebaseUser user= mAuth.getCurrentUser();
                String eMail= user.getEmail().toString();
                UUID uuid = UUID.randomUUID();
                String uuidstring = uuid.toString();
                myref.child("Gonderi").child(uuidstring).child("downloadurl").setValue(downloadurl);
                myref.child("Gonderi").child(uuidstring).child("yorum").setValue(yorumun);
                myref.child("Gonderi").child(uuidstring).child("email").setValue(eMail);

                Toast.makeText(getApplicationContext(),"Gönderiniz Paylaşıldı..",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void select(View v){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }

        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

        image = data.getData();

            try {
              Bitmap selectimg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
                resim.setImageBitmap(selectimg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
