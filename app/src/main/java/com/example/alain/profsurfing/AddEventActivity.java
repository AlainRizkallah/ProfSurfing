package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddEventActivity extends Activity {
    public EditText begtimeEdit, endtimeEdit, courseEdit, moreinfoEdit;
    public String newbeginningtime, newendtime, newcourse, userId, newmoreinfo, randomValue;
    private DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        begtimeEdit = findViewById(R.id.beginningtime_edit);
        endtimeEdit = findViewById(R.id.endtime_edit);
        courseEdit = findViewById(R.id.course_edit);
        moreinfoEdit = findViewById(R.id.moreinfo_edit);
        navigation();
    }



    public void validateEdit(View view) {
        newbeginningtime= begtimeEdit.getText().toString();
        newendtime= endtimeEdit.getText().toString();
        newcourse= courseEdit.getText().toString();
        newmoreinfo= moreinfoEdit.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId= user.getUid();
            mDatabase.child("users").child(userId).child("beginningtime").setValue(newbeginningtime);
            mDatabase.child("users").child(userId).child("endtime").setValue(newendtime);
            mDatabase.child("users").child(userId).child("course").setValue(newcourse);
            mDatabase.child("users").child(userId).child("moreinfo").setValue(newmoreinfo);
        } else {
        }
        Log.d("randomValue", randomValue);
        Intent intent = new Intent(AddEventActivity.this, CalendarActivity2.class);
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
                    Intent profil = new Intent(AddEventActivity.this, ProfilActivity.class);
                    startActivity(profil);
                    break;
                case R.id.menu_calendar:
                    Intent calendar = new Intent(AddEventActivity.this, CalendarActivity2.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(AddEventActivity.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(AddEventActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    break;
            }
            return false;
        });
    }
}
