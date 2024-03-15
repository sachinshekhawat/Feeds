package com.example.inshortsclone.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inshortsclone.FeedItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel:ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _feedItems = MutableLiveData<List<FeedItem>>()
    val feedItems: LiveData<List<FeedItem>> = _feedItems

    fun fetchDataFromFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            val documents = firestore.collection("dashboard").get().await()
            val feedList = mutableListOf<FeedItem>()
            for (uidDocument in documents) {
                val name = firestore.collection("journalists").document(uidDocument.id).get().await().get("name").toString()
                val articleSnapshot = firestore.collection("dashboard").document(uidDocument.id).collection("articles").get().await()
                for (artDoc in articleSnapshot) {
                    val link = artDoc.toObject(Article::class.java).link
                    val desc = artDoc.toObject(Article::class.java).description
                    feedList.add(FeedItem(link, desc, name))
                }
            }
            _feedItems.postValue(feedList)
        }
    }
}