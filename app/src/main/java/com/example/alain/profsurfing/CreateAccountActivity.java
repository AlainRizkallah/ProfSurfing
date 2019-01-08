package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends Activity {
    private FirebaseAuth mAuth;
    public EditText email, pw,pw2;
    private DatabaseReference mDatabase;
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email=findViewById(R.id.editTextEmail);
        pw=findViewById(R.id.editTextPassword);
        pw2=findViewById(R.id.editTextPassword2);

    }
    private void createAccount(String email, String password) {
        Log.d("createacc", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("createsuc", "createUserWithEmail:success");
                            Toast.makeText(CreateAccountActivity.this, "Authentication succ.",
                                    Toast.LENGTH_SHORT).show();
                            Intent profile_intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                            startActivity(profile_intent);
                           // writeNewUser(task.getResult().getUser().getUid(),firstName.getText().toString(),lastName.getText().toString(),tutorswitch.isChecked());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("createfail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        if(pw.getText().toString().equals(pw2.getText().toString()) )
                return true;
        else
            return false;
    }
    private void writeNewUser(String userId, String firstname, String lastname, boolean tutor) {
        mDatabase.child("users").child(userId).setValue(userId);
        mDatabase.child("users").child(userId).child("firstname").setValue(firstname);
        mDatabase.child("users").child(userId).child("lastname").setValue(lastname);
        mDatabase.child("users").child(userId).child("tutor").setValue(tutor);

    }
    public void CreateAccountButton(View view) {
        createAccount(email.getText().toString(),pw.getText().toString());
    }
}
