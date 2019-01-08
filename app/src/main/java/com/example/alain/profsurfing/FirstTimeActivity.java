package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class FirstTimeActivity extends Activity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public EditText firstName, lastName, city, school, studyLevel, weaknesses, job, topics;
    public Switch tutorswitch;
    private LinearLayout jobLayout, weaknessesLayout, topicsLayout, schoolLayout;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = (FirebaseUser) getIntent().getParcelableExtra("user");
        firstName=findViewById(R.id.firstName_edit);
        lastName=findViewById(R.id.lastName_edit);
        city=findViewById(R.id.city_edit);
        school=findViewById(R.id.school_edit);
        studyLevel=findViewById(R.id.studyLevel_edit);
        weaknesses=findViewById(R.id.weaknesses_edit);
        job=findViewById(R.id.job_edit);
        topics=findViewById(R.id.topics_edit);
        jobLayout = findViewById(R.id.jobLayout);
        weaknessesLayout = findViewById(R.id.weaknessesLayout);
        topicsLayout = findViewById(R.id.topicsLayout);
        schoolLayout = findViewById(R.id.schoolLayout);
        tutorswitch=findViewById(R.id.switch1);
        schoolLayout.setVisibility(View.VISIBLE);
        weaknessesLayout.setVisibility(View.VISIBLE);
        jobLayout.setVisibility(View.GONE);
        topicsLayout.setVisibility(View.GONE);
        tutorswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    private void writeNewUser(String userId) {
        mDatabase.child("users").child(userId).setValue(userId);
        mDatabase.child("users").child(userId).child("firstname").setValue(firstName.getText().toString());
        mDatabase.child("users").child(userId).child("lastname").setValue(lastName.getText().toString());
        mDatabase.child("users").child(userId).child("city").setValue(city.getText().toString());
        mDatabase.child("users").child(userId).child("school").setValue(school.getText().toString());
        mDatabase.child("users").child(userId).child("studyLevel").setValue(studyLevel.getText().toString());
        mDatabase.child("users").child(userId).child("weaknesses").setValue(weaknesses.getText().toString());
        mDatabase.child("users").child(userId).child("job").setValue(job.getText().toString());
        mDatabase.child("users").child(userId).child("tutor").setValue(tutorswitch.isChecked());
        mDatabase.child("users").child(userId).child("topics").setValue(topics.getText().toString());
    }

    public void startButton(View view) {
        Boolean allStudentFieldsFilled = !tutorswitch.isChecked() && !lastName.getText().toString().isEmpty() && !firstName.getText().toString().isEmpty() && !city.getText().toString().equals("") && !school.getText().toString().isEmpty() && !studyLevel.getText().toString().isEmpty() && !weaknesses.getText().toString().isEmpty();
        Boolean allTutorFieldsFilled = tutorswitch.isChecked() && !lastName.getText().toString().isEmpty() && !firstName.getText().toString().isEmpty() && !city.getText().toString().equals("") && !studyLevel.getText().toString().isEmpty() && !job.getText().toString().isEmpty() && !topics.getText().toString().isEmpty();

        if(allStudentFieldsFilled || allTutorFieldsFilled){
            writeNewUser(user.getUid());
            Intent profil_intent = new Intent(this, ProfilActivity.class);
            startActivity(profil_intent);
        } else {
            Toast.makeText(FirstTimeActivity.this, "Please fill all fields",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
