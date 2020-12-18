package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup_page.*


class SignupPage : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var awesomeValidation: AwesomeValidation
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        auth = FirebaseAuth.getInstance()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
        //Input Validation
        awesomeValidation.addValidation(
            this, R.id.inputName,
            "[a-zA-Z\\s]+", R.string.invalid_name
        )
        awesomeValidation.addValidation(
            this, R.id.inputEmail,
            Patterns.EMAIL_ADDRESS, R.string.invalid_email
        )
        awesomeValidation.addValidation(
            this, R.id.inputInterest,
            ".{8,}", R.string.invalid_password
        )
        awesomeValidation.addValidation(
            this, R.id.confirmPassword,
            R.id.inputInterest, R.string.different_password
        )
        btnSignup = findViewById(R.id.btnSignup)
        btnSignup.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            val repassword = confirmPassword.text.toString().trim()

            if (awesomeValidation.validate()) {
                if(email.isEmpty()){
                    inputEmail.error = "Email is required, please fill in."
                    inputEmail.requestFocus()
                    return@setOnClickListener
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.error = "The e-mail address enter is invalid"
                    inputEmail.requestFocus()
                    return@setOnClickListener
                }
                else if(password.isEmpty() || password.length < 8){
                    inputPassword.error = "Use at least 8 characters for your secure password"
                    inputPassword.requestFocus()
                    return@setOnClickListener
                }
                else if(repassword!=password){
                    confirmPassword.error = "Password confirmation doesn\\'t match password, please try it again."
                    confirmPassword.requestFocus()
                    return@setOnClickListener
                }

                registerUser(email, password)
            }


        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    ref = FirebaseDatabase.getInstance().getReference("Users")
                    val nama = inputName.text.toString()
                    val address = "Please fill in your data"
                    val interest = "Please fill in your data"
                    val user = Users(nama,address,interest)
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                    ref.child(userId).setValue(user).addOnCompleteListener {
                        Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(applicationContext, LoginPage::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun login(view: View) {
        val intent = Intent(applicationContext, LoginPage::class.java)
        startActivity(intent)
    }



}

