package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    private EditText input_email,input_password;
    private Button btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        input_email = (EditText) findViewById(R.id.inputEmail);
        input_password = (EditText) findViewById(R.id.inputPassword);
        btnlogin =  (Button) findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                Log.i("password", password);
                if(password.equals("passwordsalah")){
                    Intent a = new Intent(LoginScreen.this, SignupScreen.class);
                    startActivity(a);
                }
                else{
                    Intent c = new Intent(LoginScreen.this, GettingStarted.class);
                    startActivity(c);
                }
            }
        });
    }

    public void register(View view){
        Intent i = new Intent(LoginScreen.this, SignupScreen.class);
        startActivity(i);
    }
}