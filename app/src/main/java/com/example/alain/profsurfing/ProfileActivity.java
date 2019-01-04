package com.example.alain.profsurfing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends Activity {
    TextView nomPrenom;
    private ValueEventListener mPostListener;
    private DatabaseReference mPostReference;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = FirebaseDatabase.getInstance();
        nomPrenom = findViewById(R.id.textView_nomPrenom);
        BottomNavigationView mBottomNavigation =(BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = mBottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        mBottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_profile:
                    break;
                case R.id.menu_calendar:
                    Intent calendar = new Intent(ProfileActivity.this, CalendarActivity.class);
                    startActivity(calendar);
                    break;
                case R.id.menu_notifications:
                    Intent notification = new Intent(ProfileActivity.this, NotificationActivity.class);
                    startActivity(notification);
                    break;
                case R.id.menu_search:
                    Intent search = new Intent(ProfileActivity.this, SearchActivity.class);
                    startActivity(search);
                    break;
                case R.id.menu_edit:
                    Intent edit = new Intent(ProfileActivity.this, EditActivity.class);
                    startActivity(edit);
                    break;
            }

            return false;
        });
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);

        // Setting group indicator null for custom indicator
        expandableListView.setGroupIndicator(null);

        setItems();
        setListener();
    }

    public void update_info(){

    }
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            Post post = dataSnapshot.getValue();
            // ...
        }
    };
mPostReference.addValueEventListener(postListener);


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent profile_intent = new Intent(this, MainActivity.class);
        startActivity(profile_intent);
    }

    // Setting headers and childs to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();

        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list Ce que je mets comme headers
        header.add("Weakness(es)");
        header.add("Study Level");
        header.add("School/College");
        header.add("City");

        // Adding child data Ce que je mets dans le 1er child
        child1.add("Mathematics");
        // Adding child data Ce que je mets dans le 2 e child
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  - " + " : Child" + i);

        }
        // Adding child data Ce que je mets dans le 3e child
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  - " + " : Child" + i);

        }
        // Adding child data Ce que je mets dans le 4e child
        for (int i = 1; i < 3; i++) {
            child4.add("Group 4  - " + " : Child" + i);

        }


        System.out.println("heeeeeeeeeeeeeeeeeeerrrrrrrrreeeeeeee");
        System.out.println(child1);
        System.out.println(child2);
        System.out.println(child3);
        System.out.println(child4);

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);

        ExpandableListAdapter adapter = new ExpandableListAdapter(ProfileActivity.this, header, hashMap);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);

        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);
    }

    // Setting different listeners to expandablelistview
    void setListener() {
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);

        // This listener will show toast on group click
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {


                return false;
            }


        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                System.out.println("resuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuult");
                System.out.println(groupPos);
                System.out.println(childPos);
                System.out.println(id);

                return false;
            }
        });
    }

}
