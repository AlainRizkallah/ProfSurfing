package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstTimeActivity extends Activity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public EditText firstName,lastName;
    public Switch tutorswitch;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = (FirebaseUser) getIntent().getParcelableExtra("user");
        firstName=findViewById(R.id.editText3);
        lastName=findViewById(R.id.editText4);
        tutorswitch=findViewById(R.id.switch1);

    }

    private void writeNewUser(String userId, String firstname, String lastname, boolean tutor) {
        mDatabase.child("users").child(userId).setValue(userId);
        mDatabase.child("users").child(userId).child("firstname").setValue(firstname);
        mDatabase.child("users").child(userId).child("lastname").setValue(lastname);
        mDatabase.child("users").child(userId).child("tutor").setValue(tutor);

    }

    public void startButton(View view) {

        if(!lastName.getText().toString().equals("") && !firstName.getText().toString().equals("")){
            writeNewUser(user.getUid(),firstName.getText().toString(),lastName.getText().toString(),tutorswitch.isChecked());
            Log.d("atribu", user.getUid());
            Log.d("atribu2", firstName.getText().toString());
            Log.d("atribu3", lastName.getText().toString());
            Log.d("atribu4", String.valueOf(tutorswitch.isChecked()));
            Intent profile_intent = new Intent(this, ProfileActivity.class);
            startActivity(profile_intent);

        }

    }

}
