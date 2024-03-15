package com.example.inshortsclone.Authentication.JournalistAuth

import android.content.Intent
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
            val employeeId = binding.inputEmployeeId.text.toString().trim { it <= ' ' }
            val name = binding.inputName.text.toString().trim { it <= ' ' }
            val email = binding.inputEmail.text.toString().trim { it <= ' ' }
            val password = binding.inputPassword.text.toString().trim { it <= ' ' }

            Toast.makeText(this,"user id : $uid",Toast.LENGTH_LONG).show()
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

        private fun showProgressBar() {
            binding.progressBar.visibility = View.VISIBLE
        }

        private fun hideProgressBar() {
            binding.progressBar.visibility = View.GONE
        }


}