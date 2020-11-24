package com.example.mealresq

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login_page.*


class LoginPage : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 120
    }
    private lateinit var awesomeValidation: AwesomeValidation
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

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
            signIn()
        })
        facebookLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                applicationContext,
                "Facebook Login",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    Toast.makeText(this, "Google sign in success", Toast.LENGTH_SHORT).show()
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w("SignInActivity", exception.toString())
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SignInActivity", "signInWithCredential:failure")
                    Toast.makeText(this,"signInWithCredential:failure", Toast.LENGTH_SHORT).show()
                }
            }
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