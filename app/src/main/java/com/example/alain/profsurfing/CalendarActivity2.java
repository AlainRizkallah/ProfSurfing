package com.example.alain.profsurfing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity2 extends Activity {
    Button Popup, calendarprof;
    CalendarView calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);
        test();
        calendarprof = findViewById(R.id.profcalendar);
        calender = findViewById(R.id.calendarView2);
        Popup = findViewById(R.id.popup);

        Popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getApplicationContext(), PopUpActivity.class);
                startActivity(i2);
            }
        });
        calendarprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(getApplicationContext(), CalendarProfActivity.class);
                startActivity(i3);
            }
        });
        /*calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                Toast.makeText(getApplicationContext(),date+ "/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();

            }
        });*/
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                AlertDialog.Builder dialogBuilder = new
                        AlertDialog.Builder(CalendarActivity2.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_pop_up, null);
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
    }


    public void test(){
        BottomNavigationView mBottomNavigation =(BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_profil:
                    Intent profil = new Intent(CalendarActivity2.this, ProfilActivity.class);
                    startActivity(profil);
                    break;
                case R.id.menu_calendar:
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(CalendarActivity2.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(CalendarActivity2.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    Intent edit = new Intent(CalendarActivity2.this, EditActivity.class);
                    startActivity(edit);
                    break;
            }

            return false;
        });
    }


}
