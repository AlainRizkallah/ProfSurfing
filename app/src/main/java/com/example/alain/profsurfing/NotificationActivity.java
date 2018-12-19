package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        BottomNavigationView mBottomNavigation =(BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_profile:
                    Intent notification = new Intent(NotificationActivity.this, ProfileActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_calendar:
                    Intent calendar = new Intent(NotificationActivity.this, CalendarActivity.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(NotificationActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    Intent edit = new Intent(NotificationActivity.this, EditActivity.class);
                    startActivity(edit);
                    break;
            }

            return false;
        });
    }
}
