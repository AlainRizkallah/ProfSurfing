package com.example.alain.profsurfing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static android.graphics.BitmapFactory.decodeByteArray;


public class EditActivity extends Activity {
    public EditText firstNameEdit, lastNameEdit, cityEdit, schoolEdit, studyLevelEdit, weaknessesEdit;
    public String newFirstName, newLastName, newCity, userId, newSchool, newStudyLevel, newWeaknesses, randomValue;
    private DatabaseReference mDatabase;
    private ImageView imageView;
    private TextView name;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        firstNameEdit=findViewById(R.id.firstName_edit);
        lastNameEdit=findViewById(R.id.lastName_edit);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cityEdit=findViewById(R.id.city_edit);
        schoolEdit = findViewById(R.id.school_edit);
        studyLevelEdit = findViewById(R.id.studyLevel_edit);
        weaknessesEdit = findViewById(R.id.weaknesses_edit);
        name = findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        navigation();

    }




    public void validateEdit(View view) {
        newFirstName= firstNameEdit.getText().toString();
        newLastName= lastNameEdit.getText().toString();
        newCity= cityEdit.getText().toString();
        newSchool= schoolEdit.getText().toString();
        newStudyLevel = studyLevelEdit.getText().toString();
        newWeaknesses = weaknessesEdit.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId= user.getUid();
            mDatabase.child("users").child(userId).child("firstname").setValue(newFirstName);
            mDatabase.child("users").child(userId).child("lastname").setValue(newLastName);
            mDatabase.child("users").child(userId).child("school").setValue(newSchool);
            mDatabase.child("users").child(userId).child("city").setValue(newCity);
            mDatabase.child("users").child(userId).child("study_level").setValue(newStudyLevel);
            mDatabase.child("users").child(userId).child("weaknesses").setValue(newWeaknesses);
            } else {
        }
        Log.d("randomValue", randomValue);
        Intent intent = new Intent(EditActivity.this, ProfilActivity.class);
        intent.putExtra("imageId", randomValue);
        startActivity(intent);
        }

    public void navigation(){
        BottomNavigationView mBottomNavigation =(BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_profil:
                    Intent profil = new Intent(EditActivity.this, ProfilActivity.class);
                    startActivity(profil);
                    break;
                case R.id.menu_calendar:
                    Intent calendar = new Intent(EditActivity.this, CalendarActivity.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(EditActivity.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(EditActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    break;
            }
            return false;
        });
    }
}
