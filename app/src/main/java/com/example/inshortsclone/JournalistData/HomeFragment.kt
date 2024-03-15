package com.example.inshortsclone.JournalistData

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.inshortsclone.HomeAdapter
import com.example.inshortsclone.HomeItem
import com.example.inshortsclone.Models.Article
import com.example.inshortsclone.Models.HomeViewModel
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var feedAdapter: HomeAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Initialize RecyclerView and adapter
        viewPager = binding.viewPager
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.fabOptions.setOnClickListener{
           // binding.progressBar.visibility = View.VISIBLE
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.inflate(R.menu.dashmenu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.dashboard -> {
                        // Handle switching to MyFeed activity/fragment here
                        //binding.progressBar.visibility = View.GONE
                        startActivity(Intent(requireContext(), DashBoard::class.java))
                        true
                    }else -> false
                }
            }
            //binding.progressBar.visibility = View.GONE
            popupMenu.show()
        }

        feedAdapter = HomeAdapter(requireContext())
//        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

//        viewPager.adapter = feedAdapter
//        viewModel.articles.observe(viewLifecycleOwner) { articles ->
//            articles?.let {
//                feedAdapter.setData(it)
//            }
//        }
        GlobalScope.launch {
            fetchUserData()
        }

        viewPager.adapter = feedAdapter



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchUserData() {
       // binding.progressBar.visibility = View.VISIBLE // Show progress bar
        val uid = auth.currentUser?.uid

        if (uid != null) {
            val feedList = mutableListOf<HomeItem>()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    //val document = firestore.collection("dashboard").document(uid).get().await()

                    val name = firestore.collection("journalists").document(uid).get().await().get("name").toString()

                    val articleSnapshot = firestore.collection("dashboard").document(uid).collection("articles").get().await()
                    for (artDoc in articleSnapshot) {
                        val link = artDoc.toObject(Article::class.java).link
                        val desc = artDoc.toObject(Article::class.java).description
                        feedList.add(HomeItem(link, desc,name))
                        feedAdapter.setData(feedList)
                    }

                } catch (e: Exception) {
                    // Handle any errors
                    e.printStackTrace()
                }
            }
        }

    }
}