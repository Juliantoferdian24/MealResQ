package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class AfterOrder extends AppCompatActivity {

    Button orderAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_order);
        orderAgain = findViewById(R.id.orderAgain);

        orderAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterOrder.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}


