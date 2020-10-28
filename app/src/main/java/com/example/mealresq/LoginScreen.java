package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class LoginScreen extends AppCompatActivity {

    private EditText input_email,input_password;
    private Button btnlogin;
    private TextView forgot_password;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        input_email = (EditText) findViewById(R.id.inputEmail);
        input_password = (EditText) findViewById(R.id.inputPassword);
        btnlogin =  (Button) findViewById(R.id.btnLogin);
        forgot_password = (TextView) findViewById(R.id.forgotPassword);


        //Initialization Awesome Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Input Validation
        awesomeValidation.addValidation(this, R.id.inputEmail,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.inputPassword,
                ".{8,}", R.string.wrong_password);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()){
                    Intent i = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(i);
                }
//                Log.i("password", password);
//                if(password.equals("passwordsalah")){
//                    Intent a = new Intent(LoginScreen.this, SignupScreen.class);
//                    startActivity(a);
//                }
//                else{
//                    Intent c = new Intent(LoginScreen.this, GettingStarted.class);
//                    startActivity(c);
//                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, ForgotPassword.class);
                startActivity(i);
            }
        });

    }

    public void register(View view){
        Intent i = new Intent(LoginScreen.this, SignupScreen.class);
        startActivity(i);
    }
}