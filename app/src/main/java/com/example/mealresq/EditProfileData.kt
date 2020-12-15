package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile_data.*
import kotlinx.android.synthetic.main.activity_edit_profile_data.inputName
import kotlinx.android.synthetic.main.activity_signup_page.*
import java.io.File


class EditProfileData : AppCompatActivity() {
    private lateinit var imagetest: ImageView
    private lateinit var imageref: StorageReference
    private lateinit var name: TextView
    private lateinit var address: TextView
    private lateinit var interest: TextView
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_data)

        name = findViewById(R.id.inputName)
        address = findViewById(R.id.inputAddress)
        interest = findViewById(R.id.inputInterest)
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

        btnUpdate.setOnClickListener {
            val name = inputName.text.toString()
            val address = inputAddress.text.toString()
            val interest = inputInterest.text.toString()

            ref = FirebaseDatabase.getInstance().getReference("Users")
            val user = Users(name,address,interest)
            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            ref.child(userId).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}