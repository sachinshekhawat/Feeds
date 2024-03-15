package com.example.inshortsclone.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inshortsclone.HomeItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel:ViewModel() {
    private val _articles = MutableLiveData<List<HomeItem>>()
    val articles: LiveData<List<HomeItem>> = _articles

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

     fun fetchUserData() {
        val uid = auth.currentUser?.uid
        uid?.let { userId ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val feedList = mutableListOf<HomeItem>()
                    val name = firestore.collection("journalists").document(userId).get().await().get("name").toString()
                    val articleSnapshot = firestore.collection("dashboard").document(userId).collection("articles").get().await()
                    for (artDoc in articleSnapshot) {
                        val link = artDoc.toObject(Article::class.java).link
                        val desc = artDoc.toObject(Article::class.java).description
                        feedList.add(HomeItem(link, desc, name))
                    }
                    _articles.postValue(feedList)
                } catch (e: Exception) {
                    // Handle any errors
                    e.printStackTrace()
                }
            }
        }
    }
}