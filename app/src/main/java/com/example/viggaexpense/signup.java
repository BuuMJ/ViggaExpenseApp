package com.example.viggaexpense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView btnloginSignup;
    EditText fullnameSignup, usernameSignup, passwordSignup;

    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnloginSignup = findViewById(R.id.logintextSignup);
        fullnameSignup = findViewById(R.id.fullnameSignup);
        usernameSignup = findViewById(R.id.usernameSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        sharedPreferences = getSharedPreferences("account_info", MODE_PRIVATE);
        signup = findViewById(R.id.signup);
        btnloginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginpageSignup = new Intent(signup.this, login.class);
                startActivity(loginpageSignup);
            }
        });
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String newUsername = usernameSignup.getText().toString();
                String existingUsername = sharedPreferences.getString("KEY_USERNAME", "");
                if(existingUsername.equals(newUsername)){
                    Toast.makeText(signup.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("KEY_FULLNAME",fullnameSignup.getText().toString());
                    editor.putString("KEY_USERNAME",usernameSignup.getText().toString());
                    editor.putString("KEY_PASSWORD",passwordSignup.getText().toString());
                    editor.apply();
                    Toast.makeText(signup.this,"Sign Up Success", Toast.LENGTH_SHORT).show();
                    Intent success = new Intent(signup.this, login.class);
                    startActivity(success);
                }
            }
        });
    }
}