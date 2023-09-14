package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView btnlogin;
    Button btngosignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin = findViewById(R.id.logintext);
        btngosignup = findViewById(R.id.goSignUp);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginpage = new Intent(MainActivity.this, login.class);
                startActivity(loginpage);
            }
        });
        btngosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signuppage = new Intent(MainActivity.this, signup.class);
                startActivity(signuppage);
            }
        });
    }
}