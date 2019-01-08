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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static android.graphics.BitmapFactory.decodeByteArray;


public class EditActivity extends Activity {
    public EditText firstNameEdit, lastNameEdit, cityEdit, schoolEdit, studyLevelEdit, weaknessesEdit, jobEdit, topicsEdit;
    public String valueToDisplay, newFirstName, newLastName, newCity, userId, newSchool, newStudyLevel, newWeaknesses, newJob, newTopics;
    private DatabaseReference mDatabase, reference;
    private TextView test;
    private LinearLayout jobLayout, weaknessesLayout, topicsLayout, schoolLayout;
    private Switch isTutorSwitch;
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
        jobEdit = findViewById(R.id.job_edit);
        topicsEdit = findViewById(R.id.topics_edit);
        jobLayout = findViewById(R.id.jobLayout);
        weaknessesLayout = findViewById(R.id.weaknessesLayout);
        topicsLayout = findViewById(R.id.topicsLayout);
        schoolLayout = findViewById(R.id.schoolLayout);
        isTutorSwitch = findViewById(R.id.switch1);
        test = findViewById(R.id.test);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        navigation();
        getTutorValue(new ProfilActivity.MyCallback() {
            @Override
            public void onCallback(String value, TextView textView) {
                if(value.equals("false")){
                    jobLayout.setVisibility(View.GONE);
                    topicsLayout.setVisibility(View.GONE);
                    isTutorSwitch.setChecked(false);
                }else {
                    schoolLayout.setVisibility(View.GONE);
                    weaknessesLayout.setVisibility(View.GONE);
                    isTutorSwitch.setChecked(true);
                }
            }
        });
        isTutorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    schoolLayout.setVisibility(View.GONE);
                    weaknessesLayout.setVisibility(View.GONE);
                    jobLayout.setVisibility(View.VISIBLE);
                    topicsLayout.setVisibility(View.VISIBLE);

                } else {
                    schoolLayout.setVisibility(View.VISIBLE);
                    weaknessesLayout.setVisibility(View.VISIBLE);
                    jobLayout.setVisibility(View.GONE);
                    topicsLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    public void getTutorValue(ProfilActivity.MyCallback myCallback){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            userId = user.getUid();
            reference = mDatabase.child("users/" + userId + "/tutor");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        valueToDisplay = dataSnapshot.getValue().toString();
                        myCallback.onCallback(valueToDisplay, test);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Toast.makeText(EditActivity.this, "An error occured, please try later",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else {

        }
    }


    public void validateEdit(View view) {
        newFirstName= firstNameEdit.getText().toString();
        newLastName= lastNameEdit.getText().toString();
        newCity= cityEdit.getText().toString();
        newSchool= schoolEdit.getText().toString();
        newStudyLevel = studyLevelEdit.getText().toString();
        newWeaknesses = weaknessesEdit.getText().toString();
        newJob = jobEdit.getText().toString();
        newTopics = topicsEdit.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Boolean allFieldsEmpty = newFirstName.isEmpty() && newLastName.isEmpty() && newCity.isEmpty() && newSchool.isEmpty() && newStudyLevel.isEmpty() && newWeaknesses.isEmpty() && newTopics.isEmpty() && newJob.isEmpty();
        if (user != null) {
            userId= user.getUid();
            updateDatabase("firstname",newFirstName);
            updateDatabase("lastname",newLastName);
            updateDatabase("school",newSchool);
            updateDatabase("city",newCity);
            updateDatabase("study_level",newStudyLevel);
            updateDatabase("weaknesses",newWeaknesses);
            updateDatabase("job",newJob);
            updateDatabase("topics",newTopics);
            updateDatabase("tutor",String.valueOf(isTutorSwitch.isChecked()));
            if(allFieldsEmpty) {
                Toast.makeText(EditActivity.this, "All fields are empty",
                    Toast.LENGTH_SHORT).show();}
                    else {
                Toast.makeText(EditActivity.this, "Profil updated",
                    Toast.LENGTH_SHORT).show();
            }
            } else {
        }
        }
    public void updateDatabase(String child, String value){
        if(!value.isEmpty()) {
            mDatabase.child("users").child(userId).child(child).setValue(value);
        }else {
        }
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
                    Intent calendar = new Intent(EditActivity.this, CalendarActivity2.class);
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
