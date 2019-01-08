package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.versionedparcelable.ParcelField;

public class AddEventActivity extends Activity {
    public EditText begtimeEdit, endtimeEdit, courseEdit, moreinfoEdit, adressEdit;
    public String newbeginningtime, newendtime, newcourse, userId, newmoreinfo, newadress, currentDate;
    public TextView dateEdit;
    DatabaseReference mDatabase;
    public ArrayList<Map<String,String>> events = new ArrayList<>();
    DatabaseReference refdata;
    Button submit;

    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        begtimeEdit = findViewById(R.id.beginningtime_edit);
        endtimeEdit = findViewById(R.id.endtime_edit);
        adressEdit = findViewById(R.id.address_edit);
        courseEdit = findViewById(R.id.course_edit);
        moreinfoEdit = findViewById(R.id.moreinfo_edit);
        submit = findViewById(R.id.confirmevent);
        dateEdit = findViewById(R.id.date_edit);
        Intent intent = getIntent();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentDate = intent.getStringExtra("date");
        dateEdit.setText(currentDate);
        navigation();
    }

    public void validateEdit(View view){
        newadress = adressEdit.getText().toString();
        newbeginningtime= begtimeEdit.getText().toString();
        newendtime= endtimeEdit.getText().toString();
        newcourse= courseEdit.getText().toString();
        newmoreinfo= moreinfoEdit.getText().toString();
        Map<String, String> event = new HashMap<String, String>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        event.put("currentdate", currentDate);
        event.put("newbeginningtime", newbeginningtime);
        event.put("newendtime", newendtime);
        event.put("newcourse", newcourse);
        event.put("newadress", newadress);
        event.put("newmoreinfo", newmoreinfo);
        events.add(event);
        if (user != null) {
            userId= user.getUid();
            Log.d("event", String.valueOf(event));
            mDatabase.child("users").child(userId).child("event").setValue(events);
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
