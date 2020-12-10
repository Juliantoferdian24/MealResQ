package com.example.mealresq

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.Instrumentation
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile_data.*
import kotlinx.android.synthetic.main.activity_getting_started.*
import kotlinx.android.synthetic.main.fav_fragment.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

class EditProfile : AppCompatActivity(), View.OnClickListener {

    private var filePath: Uri?= null
    internal var storage:FirebaseStorage?=null
    internal var storageReference:StorageReference?=null
    private val PICK_IMAGE_REQUEST = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        btnChoose.setOnClickListener(this)
        btnUpload.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(p0 === btnChoose)
            showFileChooser()
        else if(p0 === btnUpload)
            uploadFile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null)
        {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView1!!.setImageBitmap(bitmap)
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }
    private fun uploadFile() {
        if(filePath != null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReference!!.child("profileImage/${FirebaseAuth.getInstance().currentUser?.uid}")
            imageRef.putFile(filePath!!).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, EditProfileData::class.java)
                startActivity(intent)



            }.addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }.addOnProgressListener {taskSnapShot ->
                val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
                progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}