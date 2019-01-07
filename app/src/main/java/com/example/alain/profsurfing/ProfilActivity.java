package com.example.alain.profsurfing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static android.graphics.BitmapFactory.*;

public class ProfilActivity extends Activity {
    public TextView firstNameInfo,lastNameInfo, cityInfo, schoolInfo, weaknessesInfo, studyLevelInfo, name ;
    private DatabaseReference mDatabase, reference;
    public String userId, valueToDisplay;
    private Button btnChoose, btnUpload;
    private ImageView image;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        lastNameInfo = (TextView)findViewById(R.id.firstNameInfo);
        firstNameInfo =(TextView)findViewById(R.id.lastNameInfo);
        cityInfo = (TextView)findViewById(R.id.city_edit);
        schoolInfo = (TextView)findViewById(R.id.school_edit);
        image = (ImageView)findViewById(R.id.main_profil_icon);
        weaknessesInfo = findViewById(R.id.weaknesses_edit);
        studyLevelInfo = findViewById(R.id.studyLevel_edit);
        name = findViewById(R.id.name);
        navigationBar();
        readData(new MyCallback() {
            @Override
            public void onCallback(String value, TextView textView) {
                textView.setText(value);
            }
        });

    }
    //Android:visibility pour display composant sous condition


    public void navigationBar () {
        BottomNavigationView mBottomNavigation =(BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_profil:
                    break;
                case R.id.menu_calendar:
                    Intent calendar = new Intent(ProfilActivity.this, CalendarActivity2.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(ProfilActivity.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(ProfilActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    Intent edit = new Intent(ProfilActivity.this, EditActivity.class);
                    startActivity(edit);
                    break;
            }

            return false;
        });
    }

    public interface MyCallback {
        void onCallback(String value, TextView textView);
    }

    public void readData(MyCallback myCallback) {
        readDatum(myCallback, "/firstname", firstNameInfo);
        readDatum(myCallback, "/lastname", lastNameInfo);
        readDatum(myCallback, "/city", cityInfo);
        readDatum(myCallback, "/school", schoolInfo);
        readDatum(myCallback, "/weaknesses", weaknessesInfo);
        readDatum(myCallback, "/study_level", studyLevelInfo);
    }

    public void readDatum (MyCallback myCallback, String child, TextView textView) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            userId= user.getUid();
            reference = mDatabase.child("users/" + userId + child);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        valueToDisplay=dataSnapshot.getValue().toString();
                        myCallback.onCallback(valueToDisplay, textView);
                        final String firstname = firstNameInfo.getText().toString();
                        final String lastname = lastNameInfo.getText().toString();
                        final String names = firstname+ " " +lastname;
                        System.out.println(name);
                        name.setText(names);
                    }else {

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Toast.makeText(ProfilActivity.this, "An error occured, please try later",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent profile_intent = new Intent(this, MainActivity.class);
        startActivity(profile_intent);
    }
}