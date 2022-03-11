package com.example.roome.hotel.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.roome.MainActivity
import com.example.roome.databinding.ActivityLoginBinding
import com.example.roome.user.LoginActivity
import com.example.roome.user.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private var TAG = "xxxxxFireRepository"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var userData: HashMap<String, Any?>
    fun register(email: String, password: String, username: String, content: SignUpActivity) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "addOnSuccessListener")
                authUser.postValue(auth.currentUser)
                save(username)
                content.startActivity(Intent(content, MainActivity::class.java))
                content.finish()
                Toast.makeText(content,"Register Successful",Toast.LENGTH_LONG).show()
            } else {
                Log.d(TAG, " addOnFailureListener", task.exception)
            }
        }
    }

    private fun login(email: String, password: String, content: LoginActivity) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    authUser.postValue(auth.currentUser)
                    content.startActivity(Intent(content, MainActivity::class.java))
                    content.finish()
                    task.exception
                    Toast.makeText(content,"Login Successful",Toast.LENGTH_LONG).show()
                }
                task.isCanceled -> {

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
                task.isComplete -> {

                }
            }
        }
    }

    private fun save(username: String) {
        val email = auth!!.currentUser!!.email.toString()
        val docID = auth!!.currentUser!!.uid
        var displayName = auth!!.currentUser!!.displayName.toString()
        if (displayName == null) {
            displayName = username
        }
        val phoneNumber = auth!!.currentUser!!.phoneNumber.toString()
        val photoUrl = auth!!.currentUser!!.photoUrl.toString()
        var gender: String? = null
        var birthDay: String? = null
        userData = hashMapOf(
            "docID" to docID,
            "name" to displayName,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "image" to photoUrl,
            "nikName" to displayName,
            "gender" to gender,
            "birthDay" to birthDay
        )
        firestore.collection("Users").document(docID).set(userData).addOnCompleteListener {
            Log.d("xxxxxxxx", "add data successfully")
        }.addOnFailureListener {
            Log.d("xxxxxxxx", "add data failed")
        }
    }

    fun checkError(
        binding: ActivityLoginBinding,
        activity: LoginActivity
    ) {
        var edEmail: String = binding.etEmail.text.toString().trim()
        var edPassword: String = binding.etPassword.text.toString().trim()
        if (edEmail.isNotEmpty() && edPassword.isNotEmpty()) {
            login(edEmail, edPassword, activity)
        }
        binding.apply {
            username.value = edEmail
            password.value = edPassword

            if (edEmail.isNotEmpty()) {
                username.observe(activity, Observer {
                    tvEmailError.visibility = View.GONE
                })
            } else {
                username.observe(activity, Observer {
                    tvEmailError.visibility = View.VISIBLE
                })
            }
            if (edPassword.isNotEmpty()) {
                password.observe(activity, Observer {
                    tvPasswordError.visibility = View.GONE
                })
            } else {
                password.observe(activity, Observer {
                    tvPasswordError.visibility = View.VISIBLE
                })
            }
        }
    }

}