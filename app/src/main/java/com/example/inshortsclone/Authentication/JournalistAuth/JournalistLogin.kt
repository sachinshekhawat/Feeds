package com.example.inshortsclone.Authentication.JournalistAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.inshortsclone.Authentication.ForgotPasswordActivity
import com.example.inshortsclone.Authentication.WelcomeActivity
import com.example.inshortsclone.JournalistData.DashBoard
import com.example.inshortsclone.JournalistData.SetUpActivity
import com.example.inshortsclone.Models.HomeViewModel
import com.example.inshortsclone.Models.MainViewModel
import com.example.inshortsclone.databinding.ActivityJournalistLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JournalistLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityJournalistLoginBinding
    private var email = ""

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalistLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.forgotPassword.setOnClickListener{
            showProgressBar()
            Toast.makeText(this,"Don't worry,we will help you fix your issue here!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
            hideProgressBar()
        }

        binding.gotoRegisterJour.setOnClickListener{
            showProgressBar()
            Toast.makeText(this,"Hey Journalist Register Here ",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,JournalistRegister::class.java))
            hideProgressBar()
            finish()
        }


        binding.loginJourButton.setOnClickListener{
            val employeeId = binding.inputEmployeeId.text.toString().trim{ it<= ' ' }
            val password = binding.inputPassword.text.toString().trim { it<= ' ' }

            if (employeeId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgressBar()
            // Check if employee ID and password match
            firestore.collection("journalists")
                .whereEqualTo("employeeId",employeeId)
                .get()
                .addOnSuccessListener { documents->

                    if(!documents.isEmpty){
                        //If EmployeeId Exists ,check password
                        val userDoc = documents.documents[0]
                        email = userDoc.getString("email").toString()
                        val storedPassword = userDoc.getString("password")
                        if (storedPassword == password) {
                            // Password matches, sign in the journalist
                            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                                Toast.makeText(this, "Sign in success as journalist", Toast.LENGTH_SHORT).show()
                                // Proceed with your logic here
                                val uid = auth.currentUser?.uid
                                Toast.makeText(this,"user id : $uid",Toast.LENGTH_LONG).show()
                                Toast.makeText(this,"Data Loading...",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,SetUpActivity::class.java))
                                GlobalScope.launch {
                                    delay(1000)
                                }
                            }
                            hideProgressBar()
                        } else {
                            // Password does not match
                            Toast.makeText(this, " Incorrect Password !", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        // Employee ID does not exist
                        Toast.makeText(this, "Invalid credentials/ Employee is not Registered ! ", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { e ->
                    hideProgressBar()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}