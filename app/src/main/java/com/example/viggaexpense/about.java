package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class about extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu, closeMenuIcon;
    LinearLayout nav_home, nav_newtrip, nav_listtrip, nav_about, nav_logout;
    TextView titleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mapping();
        nav_about.setBackgroundColor(getResources().getColor(R.color.nav_color));
        titleMenu.setText("About Us");
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
                redirectActivity(about.this, home.class);
            }
        });
        nav_newtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(about.this, newtrip.class);
            }
        });
        nav_listtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(about.this, listtrip.class);
            }
        });
        nav_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.commit();
                redirectActivity(about.this, MainActivity.class);
                Toast.makeText(about.this, "Signed out successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void mapping() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        menu = (ImageView)findViewById(R.id.menu);
        nav_home = (LinearLayout)findViewById(R.id.home);
        nav_newtrip = (LinearLayout)findViewById(R.id.newtrip);
        nav_listtrip = (LinearLayout)findViewById(R.id.listtrip);
        nav_about = (LinearLayout)findViewById(R.id.about);
        nav_logout = (LinearLayout)findViewById(R.id.logout);
        titleMenu = (TextView)findViewById(R.id.titleMenu);
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