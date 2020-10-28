package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class SignupScreen extends AppCompatActivity {

    EditText input_name, input_email, input_password, confirm_password;
    Button btnSignup;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        input_name = (EditText) findViewById(R.id.inputName);
        input_email = (EditText) findViewById(R.id.inputEmail);
        input_password = (EditText) findViewById(R.id.inputPassword);
        confirm_password = (EditText) findViewById(R.id.confirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        //Initialization Awesome Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Input Validation
        awesomeValidation.addValidation(this, R.id.inputName,
                "[a-zA-Z\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.inputEmail,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.inputPassword,
                ".{8,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.confirmPassword,
                R.id.inputPassword, R.string.different_password);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()){
                    Intent i = new Intent(SignupScreen.this, LoginScreen.class);
                    startActivity(i);
                }
            }
        });
    }
    public void login(View view){
        Intent i = new Intent(SignupScreen.this, LoginScreen.class);
        startActivity(i);
    }
}