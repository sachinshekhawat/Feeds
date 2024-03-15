package com.example.inshortsclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.inshortsclone.Authentication.WelcomeActivity

import com.example.inshortsclone.Models.MainViewModel
import com.example.inshortsclone.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
            inshorts ka replica
            authentication ,
            signin as journalist -> profile , phone no.,name -->
            upload thumbnail/video , DESCRIPTION
            someone is opening app , image-> thumbnail/video followed by desc
        */
// http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4

class MainActivity : AppCompatActivity() {

    private var viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Recent Feeds"
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        viewPager = binding.viewPager

        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Set up a LinearLayoutManager with vertical orientation for the RecyclerView
        //val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Attach the layout manager to the ViewPager2

        feedAdapter = FeedAdapter(this)


//        GlobalScope.launch {
//            viewModel.fetchDataFromFirestore()
//            delay(500)
//        }

        viewPager.adapter = feedAdapter
       // viewModel.fetchDataFromFirestore()

        viewModel.feedItems.observe(this) { feedItems ->
            feedAdapter.updateFeedItems(feedItems)
            binding.progressBar.visibility = View.GONE
        }


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
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
//    private fun fetchDataFromFirestore() {
//        // Query the "dashboard" collection
//        GlobalScope.launch(Dispatchers.Main) {
//            val documents = firestore.collection("dashboard")
//                .get().await()
//            delay(1000)
//            Toast.makeText(this@MainActivity, "coroutine started ${documents.size()}", Toast.LENGTH_SHORT).show()
//
//            val feedList = mutableListOf<FeedItem>()
//            for (uidDocument in documents) {
//                // Fetch journalist's name
//                val name = firestore.collection("journalists").document(uidDocument.id).get().await().get("name").toString()
//                // Wait for this operation to complete
//
//                // Fetch articles
//                Toast.makeText(this@MainActivity, "inside loop", Toast.LENGTH_SHORT).show()
//                val articleSnapshot = firestore.collection("dashboard").document(uidDocument.id).collection("articles").get().await()
//                for (artDoc in articleSnapshot) {
//                    val link = artDoc.toObject(Article::class.java).link
//                    val desc = artDoc.toObject(Article::class.java).description
//                    Toast.makeText(this@MainActivity,"$link $desc", Toast.LENGTH_SHORT).show()
//
//                    feedList.add(FeedItem(link, desc, name))
//                }
//
//            }
//
//            feedAdapter =  FeedAdapter(this@MainActivity, feedList)
//            // Update RecyclerView with fetched data
//            binding.rvFeed.layoutManager = LinearLayoutManager(this@MainActivity)
//            binding.rvFeed.adapter = feedAdapter
//            Toast.makeText(this@MainActivity, "coroutine end", Toast.LENGTH_SHORT).show()
//
//        }
//        binding.progressBar.visibility = View.GONE
//    }

}