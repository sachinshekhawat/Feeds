package com.example.inshortsclone.JournalistData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.inshortsclone.Authentication.WelcomeActivity
import com.example.inshortsclone.Models.HomeViewModel
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.ActivitySetUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetUpBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val viewModel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setSupportActionBar(binding.toolbarHomefragment)
        supportActionBar?.title = "My Feeds"
        binding.bottomNav.setOnNavigationItemSelectedListener(navListener)

        // Set default fragment
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, HomeFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Handle logout action here
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Perform logout actions here
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.myfeed -> {
                val homeFragment = HomeFragment()
                GlobalScope.launch {
                    viewModel.fetchUserData()
                }
                Toast.makeText(this,"Data Loading.., Wait a bit!",Toast.LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction().replace(R.id.framelayout, homeFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                val profileFragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.framelayout, profileFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }


}