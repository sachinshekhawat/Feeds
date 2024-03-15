package com.example.inshortsclone.JournalistData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.inshortsclone.HomeItem
import com.example.inshortsclone.Models.Article
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.FragmentHomeBinding
import com.example.inshortsclone.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ProfileFragment : Fragment() {

    private  var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private  var name:String? = null
    private  var employeeId:String? = null
    private  var articleCount:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        binding.progressBar.visibility = View.VISIBLE

        binding.NeedHelp.setOnClickListener{
            Toast.makeText(requireContext(),"We can set it later!",Toast.LENGTH_SHORT).show()
        }
        fetchProfileData()

//        "Name : $name".also { binding.jname.text = it }
//        "EmployeeId : $employeeId".also { binding.jId.text = it }
//        "Articles Published  : $articleCount".also { binding.artPub.text = it }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchProfileData() {
        // binding.progressBar.visibility = View.VISIBLE // Show progress bar
        val uid = auth.currentUser?.uid

        if (uid != null) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    //val document = firestore.collection("dashboard").document(uid).get().await()
                    name = firestore.collection("journalists").document(uid).get().await().get("name").toString()
                    employeeId = firestore.collection("journalists").document(uid).get().await().get("employeeId").toString()
                    articleCount = firestore.collection("journalists").document(uid).get().await().get("articleCount").toString()

                    updateUIWithProfileData()
                    delay(1000)
                    Toast.makeText(requireContext(), "Your Profile is Updated!", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                } catch (e: Exception) {
                    // Handle any errors
                    e.printStackTrace()
                }
            }
        }

    }

    private fun updateUIWithProfileData() {
        binding.jname.text = name
        binding.jId.text = "Employee ID: $employeeId"
        binding.artPub.text = "Articles Published: $articleCount"
    }


}