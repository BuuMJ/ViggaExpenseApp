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

public class login extends AppCompatActivity {
    TextView btngosignupLogin;
    EditText username, password;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btngosignupLogin = findViewById(R.id.goSignupLogin);
        btnLogin = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        btngosignupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signuppageLogin = new Intent(login.this, signup.class);
                startActivity(signuppageLogin);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // lấy thông tin người dùng nhập
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();
                // lấy thông tin người dùng đã đăng kí từ kho lưu trữ shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("account_info", MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("KEY_USERNAME", "");
                String savedPassword = sharedPreferences.getString("KEY_PASSWORD", "");

                if(enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)){
                    Toast.makeText(login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, home.class);
                    startActivity(intent);
                }
                else {
                    if(!enteredUsername.equals(savedUsername)){
                        Toast.makeText(login.this, "Wrong User name", Toast.LENGTH_SHORT).show();
                    }
                    else if(!enteredPassword.equals(savedPassword)){
                        Toast.makeText(login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}