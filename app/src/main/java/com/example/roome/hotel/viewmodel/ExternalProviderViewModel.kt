package com.example.roome.hotel.viewmodel

import android.content.ContentValues

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.user.LoginActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ExternalProviderViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var callbackManager: CallbackManager
    private var firebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    val RC_SIGN_IN = 7
    private var TAG ="xxxxExternalProvider"

    fun firebaseAuthWithGoogle(idToken: String, content: LoginActivity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(content) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    Toast.makeText(content,"Login Successful",Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                } else
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
            }
    }

    fun facebookAuth(content: LoginActivity, callbackManager :CallbackManager) {
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
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(content) { task ->
                if (task.isSuccessful) {
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


