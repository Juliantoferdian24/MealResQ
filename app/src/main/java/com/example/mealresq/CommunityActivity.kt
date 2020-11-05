package com.example.mealresq

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CommunityActivity: AppCompatActivity() {

    private lateinit var facebook: ImageView
    private lateinit var instagram: ImageView
    private lateinit var twitter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_fragment)
        facebook = findViewById(R.id.facebook)
        instagram = findViewById(R.id.instagram)
        twitter = findViewById(R.id.twitter)

        facebook.setOnClickListener{
            Toast.makeText(this, "facebook", Toast.LENGTH_SHORT).show()
        }
        instagram.setOnClickListener{
            Toast.makeText(this, "instagram", Toast.LENGTH_SHORT).show()
        }
        twitter.setOnClickListener{
            Toast.makeText(this, "twitter", Toast.LENGTH_SHORT).show()
        }
    }


}

