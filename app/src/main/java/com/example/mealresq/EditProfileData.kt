package com.example.mealresq

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile_data.*
import java.io.File


class EditProfileData : AppCompatActivity() {
    private lateinit var imagetest: ImageView
    private lateinit var imageref: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_data)

        imageref = FirebaseStorage.getInstance().reference.child("profileImage/${FirebaseAuth.getInstance().currentUser!!.uid}")
        imageref.downloadUrl.addOnSuccessListener {Uri->
            val imageURL = Uri.toString()
            imagetest = findViewById(R.id.editFoto)
            Glide.with(this)
                .load(imageURL)
                .circleCrop()
                .into(imagetest)
        }

        changePicture.setOnClickListener{
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}