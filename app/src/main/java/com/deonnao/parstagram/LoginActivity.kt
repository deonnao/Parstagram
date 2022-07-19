package com.deonnao.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Check if the user is logged in. If so take them to the main activity
        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
            finish()
        }
        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username, password)
        }
        findViewById<Button>(R.id.signUpBtn).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // User successfully created a new account
                Toast.makeText(this, "Successfully created an account!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
                finish()
            } else {
                // Sign up didn't succeed
                e.printStackTrace()
                Toast.makeText(this, "Sign up unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username : String, password : String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
            // Hooray!  The user is logged in.
                Log.i(TAG, "Successfully logged in user!")
                goToMainActivity()
            } else {
                // Signup failed.  Look at the ParseException to see what happened.
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        const val TAG = "LoginActivity"
    }
}
