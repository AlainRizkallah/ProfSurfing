package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class CalendarProfActivity extends Activity {
    Button addevent;
    CalendarView calendarprof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_prof);
        addevent = findViewById(R.id.addeventbutton);
        calendarprof = findViewById(R.id.calendarView2);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(i);
            }
        });

        calendarprof.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                Intent addEvent = new Intent(getApplicationContext(), AddEventActivity.class);
                addEvent.putExtra("date",date+ "/"+(month+1)+"/"+year);
                startActivity(addEvent);
            }
        });
    }
}
