package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*


class LoginPage : AppCompatActivity() {

    private lateinit var awesomeValidation: AwesomeValidation
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        auth = FirebaseAuth.getInstance()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)

        awesomeValidation.addValidation(
            this, R.id.inputEmail,
            Patterns.EMAIL_ADDRESS, R.string.invalid_email
        )
        awesomeValidation.addValidation(
            this, R.id.inputPassword,
            ".{8,}", R.string.wrong_password
        )

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (awesomeValidation.validate()) {

                if(email.isEmpty()){
                    inputEmail.error = "Email is required, please fill in."
                    inputEmail.requestFocus()
                    return@setOnClickListener
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.error = R.string.invalid_email.toString()
                    inputEmail.requestFocus()
                    return@setOnClickListener
                }
                else if(password.isEmpty() || password.length < 8){
                    inputPassword.error = R.string.invalid_password.toString()
                    inputPassword.requestFocus()
                    return@setOnClickListener
                }
                loginUser(email, password)
            }

        }

        forgotPassword.setOnClickListener(View.OnClickListener {
            val i = Intent(this@LoginPage, ForgotPassword::class.java)
            startActivity(i)
        })
        googleLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                applicationContext,
                "Google Login",
                Toast.LENGTH_SHORT
            ).show()
        })
        facebookLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                applicationContext,
                "Facebook Login",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent = Intent(this@LoginPage, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun register(view: View) {
        val i = Intent(this@LoginPage, SignupPage::class.java)
        startActivity(i)
    }


    override fun onBackPressed() {

    }

}