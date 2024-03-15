package com.example.inshortsclone.Authentication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.inshortsclone.MainActivity
import com.example.inshortsclone.Models.MainViewModel
import com.example.inshortsclone.R
import com.example.inshortsclone.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import org.checkerframework.common.returnsreceiver.qual.This

class LoginActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    private lateinit var  mprogressdialog: Dialog
    private lateinit var mauth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mauth = FirebaseAuth.getInstance()

        binding.forgotPassword.setOnClickListener{
            showBar()
            Toast.makeText(this,"Don't Worry Fix Your Password Here!",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
            hideBar()
        }

        binding.gotoRegister.setOnClickListener{
            showBar()
            Toast.makeText(this,"Register Here",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,RegisterationActivity::class.java))
            hideBar()
        }

        mauth = FirebaseAuth.getInstance()

        binding.lgnButton.setOnClickListener{
            signinregistereduser()
        }

    }

    private fun signinregistereduser() {
        val email1:String = binding.inputEmail.text.toString().trim{it<= ' ' }
        val password1:String =binding.inputPassword.text.toString().trim{it<=' '}
        if(revalidate1(email1, password1))
        {
            showprogressdialog("Signing In...")
            mauth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this){
                    task-> hideprogressdialog()
                if(task.isSuccessful)
                {
                    //fetching  data using viewmodel before Actually Loging in
                    GlobalScope.launch {
                        viewModel.fetchDataFromFirestore()
                    }
                    val layout123=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                    val   toast4: Toast = Toast(this)
                    toast4.view=layout123
                    val txtmsg12: TextView = layout123.findViewById(R.id.textview_toast)
                    txtmsg12.setText( "You Have Signed In Successfully")
                    toast4.duration.toLong()
                    toast4.show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                    val toast1:Toast= Toast(this)
                    toast1.view=layout1
                    val txtmsg1: TextView =layout1.findViewById(R.id.textview_toast1)
                    txtmsg1.setText("Authentication failed,Please enter correct credentials.")
                    toast1.duration.toShort()
                    toast1.show()
                }
            }

        }

    }

    private fun revalidate1(
        email1: String,
        password1: String
    )
            : Boolean {
        return when{
            TextUtils.isEmpty(email1)->{
                showerrorsnackbar("Please enter your Email Address")
                false
            }
            TextUtils.isEmpty(password1)->{
                showerrorsnackbar("Please enter your Password")
                false
            }
            else->{
                true
            }

        }




    }
    fun showprogressdialog(Text:String){
        mprogressdialog = Dialog(this)
        mprogressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mprogressdialog.setContentView(R.layout.dialog_progress)
        mprogressdialog.setCancelable(false)
        mprogressdialog.findViewById<TextView>(R.id.tv_progress_text).text=Text

        mprogressdialog.show()
        Handler().postDelayed({},2000)


    }
    fun hideprogressdialog()
    {
        mprogressdialog.dismiss()
    }
    fun showerrorsnackbar(message:String){
        // gives the root element of a view without actually knowing its id
        val snackbar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackbarview=snackbar.view
        snackbarview.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideBar(){
        binding.progressBar.visibility = View.GONE
    }
}
