package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity {
    TextView fullnameuser;
    EditText test;
    DrawerLayout drawerLayout;
    ImageView menu, closeMenuIcon;
    LinearLayout nav_home, nav_newtrip, nav_listtrip, nav_about;
    Button goAddTrip, goListTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapping();
        goAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripPage = new Intent(home.this, newtrip.class);
                startActivity(addTripPage);
            }
        });
        goListTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listTripPage = new Intent(home.this, listtrip.class);
                startActivity(listTripPage);
            }
        });
        nav_home.setBackgroundColor(getResources().getColor(R.color.nav_color));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        closeMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        nav_newtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(home.this, newtrip.class);
            }
        });
        nav_listtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(home.this, listtrip.class);
            }
        });
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(home.this, about.class);
            }
        });
    }


    private void mapping() {
        goAddTrip = (Button)findViewById(R.id.goAddTrip);
        goListTrip = (Button)findViewById(R.id.goTripList);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        menu = (ImageView)findViewById(R.id.menu);
        nav_home = (LinearLayout)findViewById(R.id.home);
        nav_newtrip = (LinearLayout)findViewById(R.id.newtrip);
        nav_listtrip = (LinearLayout)findViewById(R.id.listtrip);
        nav_about = (LinearLayout)findViewById(R.id.about);
        closeMenuIcon = (ImageView)findViewById(R.id.closeMenuIcon);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}