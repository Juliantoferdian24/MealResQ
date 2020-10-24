package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class GettingStarted extends AppCompatActivity {

    CircularProgressButton gettingStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        gettingStarted = (CircularProgressButton) findViewById(R.id.gettingStarted);
        gettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<String, String, String> started = new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "done";
                    }
                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals("done")){
                            Intent i = new Intent(GettingStarted.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                };

                gettingStarted.startAnimation();
                started.execute();
            }
        });
    }
}