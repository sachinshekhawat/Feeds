package com.example.inshortsclone.JournalistData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import com.example.inshortsclone.Authentication.WelcomeActivity
import com.example.inshortsclone.MainActivity
import com.example.inshortsclone.R

import com.example.inshortsclone.databinding.ActivityDashBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DashBoard : AppCompatActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.fabOptions.setOnClickListener{
            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.top_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.myfeed -> {
                        // Handle switching to MyFeed activity/fragment here
                       startActivity(Intent(this,SetUpActivity::class.java))
                        true
                    }
                    R.id.logout -> {
                        // Handle logout option
                        logout()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        val user = auth.currentUser
        binding.btnPublish.setOnClickListener {
            if (user != null) {
                Toast.makeText(this,"user id : ${user.uid}",Toast.LENGTH_LONG).show()
            }
            val link = binding.etLink.text.toString().trim { it <= ' ' }
            val description = binding.etDescription.text.toString().trim { it <= ' ' }

            if (link.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



                // Create a HashMap to store data
                val data = hashMapOf(
                    "link" to link,
                    "description" to description
                )

                // Add data to Firestore collection "dashboard"
//            if (uid != null) {
            if (auth.currentUser != null) {
                if (user != null) {
                    firestore.collection("dashboard")
                        .document(user.uid).set(hashMapOf(
                            "count" to 1
                        ))

                    val collectionReference = firestore.collection("dashboard")
                    val userDocumentRef = collectionReference.document(user.uid)

                    val updates = hashMapOf<String, Any>(
                        "count" to FieldValue.delete()
                    )

                    userDocumentRef.update(updates)
                        .addOnSuccessListener {
                            // Field deleted successfully
                        }
                        .addOnFailureListener { e ->
                            // Handle failure
                        }


                    firestore.collection("dashboard")
                        .document(user.uid)
                        .collection("articles").document().set(data)
                        .addOnSuccessListener {

                            updateArticleCount(user.uid)
                            Toast.makeText(this, "Data published successfully", Toast.LENGTH_SHORT)
                                .show()
                            // Clear input fields after successful publishing
                            binding.etLink.text.clear()
                            binding.etDescription.text.clear()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Failed to publish data: ${e.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                }


            }


        }

    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
//            // Navigate back to WelcomeActivity
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
    }

    private fun updateArticleCount(uid: String) {
        // Increment article count in Firestore
        GlobalScope.launch {

            try {
                firestore.collection("journalists")
                    .document(uid)
                    .update("articleCount", FieldValue.increment(1)).await()
            }catch (e: Exception) {
                // Handle any errors
                e.printStackTrace()
            }
        }
    }


}