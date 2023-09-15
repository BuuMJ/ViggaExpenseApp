package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class home extends AppCompatActivity {
    TextView fullnameuser;
//    Toolbar toolbar;
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ListView listView;
    EditText test;
    Button goAddTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapping();
        SharedPreferences sharedPreferences = getSharedPreferences("account_info", MODE_PRIVATE);
        String fullnamehome = sharedPreferences.getString("KEY_FULLNAME", "");
        Log.d("MyApp", "fullnamehome: " + fullnamehome);
        fullnameuser.setText(fullnamehome);
//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
//        navigationView = (NavigationView)findViewById(R.id.navigationView);
//        listView = (ListView)findViewById(R.id.lv);
//        actionToolbar();
//        toolbar.setTitle("");
        goAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripPage = new Intent(home.this, newtrip.class);
                startActivity(addTripPage);
            }
        });
    }

    private void mapping() {
        fullnameuser = (TextView)findViewById(R.id.helloname);
        goAddTrip = (Button)findViewById(R.id.goAddTrip);
    }

//    private void actionToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_action_sidebar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//    }
}