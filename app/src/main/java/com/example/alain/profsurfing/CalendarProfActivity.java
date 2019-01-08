package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CalendarProfActivity extends Activity {
    Button addevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_prof);
        addevent = findViewById(R.id.addeventbutton);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(i);
            }
        });
    }
}
