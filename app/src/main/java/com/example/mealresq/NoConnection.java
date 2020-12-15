package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoConnection extends AppCompatActivity {

    private Button tryagain_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        tryagain_button = findViewById(R.id.tryagain_button);

        tryagain_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NoConnection.this, GettingStarted.class);
                startActivity(i);
                finish();
            }
        });
    }

}