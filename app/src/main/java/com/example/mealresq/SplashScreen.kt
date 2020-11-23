package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
        } else{
            val intent = Intent(this@SplashScreen, OnBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}