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
    public EditText begtimeEdit, endtimeEdit, courseEdit, moreinfoEdit;
    public String newbeginningtime, newendtime, newcourse, userId, newmoreinfo;
    DatabaseReference mDatabase;
    DatabaseReference refdata;
    Button submit;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    ArrayList<String> event = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        begtimeEdit = findViewById(R.id.beginningtime_edit);
        endtimeEdit = findViewById(R.id.endtime_edit);
        courseEdit = findViewById(R.id.course_edit);
        moreinfoEdit = findViewById(R.id.moreinfo_edit);
        submit = findViewById(R.id.confirmevent);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        refdata = mDatabase.child("users").child(userId);
        navigation();
    }

    public void validateEdit(View view){
        newbeginningtime= begtimeEdit.getText().toString();
        newendtime= endtimeEdit.getText().toString();
        newcourse= courseEdit.getText().toString();
        newmoreinfo= moreinfoEdit.getText().toString();
        DatabaseReference refdata = mDatabase.child("users").child(userId);
        Map<String, String> event = new HashMap<String, String>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            event.put("newbeginningtime", newbeginningtime);
            event.put("newendtime", newendtime);
            event.put("newcourse", newcourse);
            event.put("newmoreinfo", newmoreinfo);
            refdata.push().setValue(event);
        }
    }



   /* public void validateEdit(View view) {
        newbeginningtime= begtimeEdit.getText().toString();
        newendtime= endtimeEdit.getText().toString();
        newcourse= courseEdit.getText().toString();
        newmoreinfo= moreinfoEdit.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId= user.getUid();
            Map<String, String> event = new HashMap<String, String>();
            event.put("beginningtime",newbeginningtime);
            event.put("endtime",newendtime);
            event.put("course",newcourse);
            event.put("moreinfo",newmoreinfo);
            refdata.push().setValue(event);
        } else {
        }
    }
*/




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
