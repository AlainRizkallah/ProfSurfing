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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PopUpActivity extends Activity {
    public TextView BegtimeInfo,EndtimeInfo, CourseInfo, MoreinfoInfo, name ;
    private DatabaseReference mDatabase, reference;
    public String userId, valueToDisplay;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        BegtimeInfo = findViewById(R.id.Beginningtime_edit);
        EndtimeInfo = findViewById(R.id.Endtime_edit);
        CourseInfo = findViewById(R.id.Course_edit);
        MoreinfoInfo = findViewById(R.id.Moreinfo_edit);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();mDatabase = FirebaseDatabase.getInstance().getReference();
        readData(new ProfilActivity.MyCallback() {
            @Override
            public void onCallback(String value, TextView textView) {

                textView.setText(value);
            }
        });

    }
    //Android:visibility pour display composant sous condition



    public interface MyCallback {
        void onCallback(String value, TextView textView);
    }

    public void readData(ProfilActivity.MyCallback myCallback) {
        readDatum(myCallback, "/beginningtime", BegtimeInfo);
        readDatum(myCallback, "/endtime", EndtimeInfo);
        readDatum(myCallback, "/course", CourseInfo);
        readDatum(myCallback, "/moreinfo", MoreinfoInfo);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                    if (dataSnapshot.getValue() != null) {
                        valueToDisplay = dataSnapshot.getValue().toString();
                        myCallback.onCallback(valueToDisplay, name);
                    }

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

    public void readDatum (ProfilActivity.MyCallback myCallback, String child, TextView textView) {
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
                    }else {

                    }


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

