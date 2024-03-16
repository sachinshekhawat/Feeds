package com.example.inshortsclone.Authentication.JournalistAuth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.inshortsclone.JournalistData.DashBoard
import com.example.inshortsclone.JournalistData.SetUpActivity
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.ActivityJournalistRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class JournalistRegister : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityJournalistRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalistRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid.toString()
        binding.gotoLoginJour.setOnClickListener {
            Toast.makeText(this, "Already registered!, Login Here", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, JournalistLogin::class.java))
            finish()
        }

        binding.RegisterJourButton.setOnClickListener {

            val name = binding.inputName.text.toString().trim { it <= ' ' }
            val email = binding.inputEmail.text.toString().trim { it <= ' ' }
            val password = binding.inputPassword.text.toString().trim { it <= ' ' }
            val employeeId = employeeIdGen(email,name)

          //  Toast.makeText(this,"user id : $uid",Toast.LENGTH_LONG).show()
            if (employeeId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgressBar()
            firestore.collection("journalists")
                .whereEqualTo("employeeId", employeeId)
                .get()
                .addOnSuccessListener { documents ->
                    hideProgressBar()
                    if (!documents.isEmpty) {
                        // Employee ID already exists
                        Toast.makeText(
                            this,
                            "Journalist with this employee ID already exists. Please log in.",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this, JournalistLogin::class.java))
                        finish()
                    } else {
                        // Employee ID is unique, proceed with sign-up
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    if (user != null) {
                                        // Store journalist data in Firestore
                                        val journalistData = hashMapOf(
                                            "employeeId" to employeeId,
                                            "name" to name,
                                            "email" to email,
                                            "password" to password,
                                            "articleCount" to 0
                                            // Store password
                                        )
                                        showProgressBar()
                                        firestore.collection("journalists").document(user.uid)
                                            .set(journalistData)
                                            .addOnSuccessListener {

                                                Toast.makeText(
                                                    this,
                                                    "Journalist registered successfully!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                hideProgressBar()
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        SetUpActivity::class.java
                                                    )
                                                )
                                                finish()
                                            }
                                            .addOnFailureListener { e ->
                                                hideProgressBar()
                                                Toast.makeText(
                                                    this,
                                                    "Failed to register journalist: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                } else {
                                    Toast.makeText(
                                        baseContext,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
                .addOnFailureListener { e ->
                    hideProgressBar()
                    Toast.makeText(
                        this,
                        "Error checking employee ID: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

        private fun employeeIdGen(email:String , name:String):String{
            val namePrefix = name.take(4).toLowerCase()

            // Extract the last 4 characters before the @ symbol in the email
            val emailPrefix = email.substringBeforeLast('@').takeLast(4)

            // Generate 2 random digits
            val randomDigits = (99..999).random().toString().padStart(3, '0')

            val employeeId = "$namePrefix$emailPrefix$randomDigits@feeds.com"
            // Concatenate the components and append "@feeds.com"
            //sendEmail(email,employeeId)
            Toast.makeText(this,"Your EmployeeID is $employeeId",Toast.LENGTH_SHORT).show()
            return employeeId
        }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail(email: String, employeeId: String) {
        val subject = "Your Employee ID for FEEDS"
        val message = "Welcome! Your employee ID for FEEDS is: $employeeId. ,\n Remember it ! and Welcome to our small family with big vision."

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        if (intent.resolveActivity(packageManager) != null) {
           // startActivity(intent)
        } else {
            startActivity(intent)
           // Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
            binding.progressBar.visibility = View.VISIBLE
        }

        private fun hideProgressBar() {
            binding.progressBar.visibility = View.GONE
        }


}