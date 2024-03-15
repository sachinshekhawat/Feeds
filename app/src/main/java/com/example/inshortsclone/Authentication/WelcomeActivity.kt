package com.example.inshortsclone.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.example.inshortsclone.Authentication.JournalistAuth.JournalistLogin
import com.example.inshortsclone.JournalistData.SetUpActivity
import com.example.inshortsclone.MainActivity
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.ActivityWelcomeBinding
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.initialize

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var mauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )

        mauth = FirebaseAuth.getInstance()

        binding.needHelp.setOnClickListener{
            Toast.makeText(this,"We can set it later!",Toast.LENGTH_SHORT).show()
        }

        binding.jourLog.setOnClickListener{
            showBar()

            val currentUser = mauth.currentUser
            if (currentUser != null) {
                Toast.makeText(this,"Welcome , Data is Loading..",Toast.LENGTH_LONG).show()
                // If the user is already signed in, proceed to the desired activity
                startActivity(Intent(this, SetUpActivity::class.java))
                finish()
            } else {
                // If the user is not signed in, proceed to the login activity
                Toast.makeText(this,"Welcome To Journalist Window!",Toast.LENGTH_LONG).show()
                startActivity(Intent(this, JournalistLogin::class.java))
                finish()
            }
            hideBar()
            finish()
        }

        binding.userLog.setOnClickListener{
            showBar()
            Toast.makeText(this,"Welcome Dear User!",Toast.LENGTH_LONG).show()
            // Check if the user is already signed in
            val currentUser = mauth.currentUser
            if (currentUser != null) {
                // If the user is already signed in, proceed to the desired activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // If the user is not signed in, proceed to the login activity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            hideBar()
            finish()
        }
    }

    private fun showBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideBar(){
        binding.progressBar.visibility = View.GONE
    }
}