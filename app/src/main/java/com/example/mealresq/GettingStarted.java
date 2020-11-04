package com.example.mealresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class GettingStarted extends AppCompatActivity {

    CircularProgressButton gettingStarted;
    ImageView logo;
    TextView slogan;
    Boolean is_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        gettingStarted = (CircularProgressButton) findViewById(R.id.gettingStarted);
        logo = (ImageView) findViewById(R.id.logo);
        slogan = (TextView) findViewById(R.id.slogan);



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
                            if (ConnectionManager.checkConnection(getBaseContext())){
                                Intent i = new Intent(GettingStarted.this, LoginScreen.class);
                                startActivity(i);
                            } else{
                                Intent i = new Intent(GettingStarted.this, NoConnection.class);
                                startActivity(i);
                            }
                        }
                    }
                };

                gettingStarted.startAnimation();
                started.execute();
            }
        });
        logo.setY(-1000);
        logo.animate().translationYBy(1000).setDuration(1000);

        slogan.setX(-1000);
        slogan.animate().translationXBy(1000).setDuration(1000);

        gettingStarted.setY(500);
        gettingStarted.animate().translationYBy(-500).setDuration(1000);

    }
}