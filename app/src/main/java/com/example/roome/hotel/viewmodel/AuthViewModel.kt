package com.example.roome.hotel.viewmodel

import android.app.Application
import android.content.ContentValues
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
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var authUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    var currentUser: FirebaseUser? = Firebase.auth.currentUser
    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    //private var auth: FirebaseAuth = Firebase.auth
    val RC_SIGN_IN = 7
    private var TAG ="xxxxExternalProvider"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var userData: HashMap<String, Any?>


    fun register(email: String, password: String, username: String, content: SignUpActivity) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "addOnSuccessListener")
                authUser.value = auth.currentUser
                Log.i("xxxxAuthUser",authUser.toString())
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
                    authUser.value = auth.currentUser
                    Log.i("xxxxAuthUser",authUser.toString())
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

        val phoneNumber = auth!!.currentUser!!.phoneNumber.toString()
        val photoUrl = auth!!.currentUser!!.photoUrl.toString()
        var gender: String? = null
        var birthDay: String? = null
        userData = hashMapOf(
            "docID" to docID,
            "name" to username,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "image" to photoUrl,
            "nikName" to username,
            "gender" to gender,
            "birthDay" to birthDay
        )
        firestore.collection("users").document(docID).set(userData).addOnCompleteListener {
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



    fun firebaseAuthWithGoogle(idToken: String, content: LoginActivity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(content) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    Toast.makeText(content,"Login Successful",Toast.LENGTH_LONG).show()
                    authUser.value = auth.currentUser
                    Log.i("xxxxAuthUser",authUser.toString())
                } else
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
            }
    }

    fun facebookAuth(content: LoginActivity, callbackManager : CallbackManager) {
        LoginManager.getInstance().logInWithReadPermissions(
            content,
            listOf("public_profile", "email", "user_photos", "user_gender", "user_birthday")
        )
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$result")
                    handleFacebookAccessToken(result.accessToken, content)
                }
                override fun onCancel() {
                    Log.d(TAG, "onCancelled")
                }
                override fun onError(error: FacebookException) {
                    Log.d(TAG, "onError: $error")
                }
            })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken, content: LoginActivity) {
        //Get Credential
        Log.d(TAG, "handleFacebookAccessToken:$accessToken")
        val credential: AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(content) { task ->
                if (task.isSuccessful) {
                    authUser.value = auth.currentUser
                    Log.i("xxxxAuthUser",authUser.toString())
                    Toast.makeText(content,"Login Successful",Toast.LENGTH_LONG).show()
                    content.startActivity(Intent(content, MainActivity::class.java))
                    content.finish()
                } else {
                    Toast.makeText(
                        content, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}