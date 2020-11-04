package com.example.mealresq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")){
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
        } else{
            startActivity(new Intent(this, OnBoardActivity.class));
        }

    }
}
