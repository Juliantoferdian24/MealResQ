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

public class ForgotPassword extends AppCompatActivity {

    private EditText input_email;
    private Button btnReset;
    private TextView backToSignIn;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        input_email = findViewById(R.id.inputAddress);
        btnReset = findViewById(R.id.btnReset);
        backToSignIn = findViewById(R.id.backToSignIn);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Input Validation
        awesomeValidation.addValidation(this, R.id.inputAddress,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword.this, LoginPage.class);
                startActivity(i);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()){
                    Intent i = new Intent(ForgotPassword.this, LoginPage.class);
                    startActivity(i);
                }
            }
        });

    }
}