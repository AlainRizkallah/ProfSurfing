package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopUpActivity extends Activity {
    public TextView BegtimeInfo,EndtimeInfo, CourseInfo, MoreinfoInfo, name ;
    private DatabaseReference mDatabase, reference;
    public String userId, valueToDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        BegtimeInfo = findViewById(R.id.Beginningtime_edit);
        EndtimeInfo = findViewById(R.id.Endtime_edit);
        CourseInfo = findViewById(R.id.Course_edit);
        MoreinfoInfo = findViewById(R.id.Moreinfo_edit);
        navigationBar();
        readData(new ProfilActivity.MyCallback() {
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
                    Intent calendar = new Intent(PopUpActivity.this, CalendarActivity2.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(PopUpActivity.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(PopUpActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    Intent edit = new Intent(PopUpActivity.this, EditActivity.class);
                    startActivity(edit);
                    break;
            }

            return false;
        });
}
    public interface MyCallback {
        void onCallback(String value, TextView textView);
    }

    public void readData(ProfilActivity.MyCallback myCallback) {
        readDatum(myCallback, "/beginningtime", BegtimeInfo);
        readDatum(myCallback, "/endtime", EndtimeInfo);
        readDatum(myCallback, "/course", CourseInfo);
        readDatum(myCallback, "/moreinfo", MoreinfoInfo);
    }

    public void readDatum (ProfilActivity.MyCallback myCallback, String child, TextView textView) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            userId= user.getUid();
            reference = mDatabase.child("users/" + userId + child);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    valueToDisplay = dataSnapshot.getValue().toString();
                    myCallback.onCallback(valueToDisplay, textView);
                    final String beginningtime = BegtimeInfo.getText().toString();
                    final String endtime = EndtimeInfo.getText().toString();
                    final String names = beginningtime+ " " +endtime;
                    System.out.println(name);
                    name.setText(names);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Toast.makeText(PopUpActivity.this, "An error occured, please try later",
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

