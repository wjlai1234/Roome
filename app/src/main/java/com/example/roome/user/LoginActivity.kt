package com.example.roome.user

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.ActivityLoginBinding
import com.example.roome.hotel.viewmodel.AuthViewModel
import com.example.roome.hotel.viewmodel.BookViewModel
import com.example.roome.hotel.viewmodel.ExternalProviderViewModel
import com.example.roome.hotel.viewmodel.HotelViewModel
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    private val bookViewModel by lazy { ViewModelProvider(this).get(BookViewModel::class.java) }
    private val viewModel by lazy { ViewModelProvider(this).get(AuthViewModel::class.java) }
    private val externalProviderViewModel by lazy {
        ViewModelProvider(this).get(
            ExternalProviderViewModel::class.java
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()
        binding.apply {
            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
            btnGoogle.setOnClickListener {
                val gso = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val signInIntent = GoogleSignIn.getClient(this@LoginActivity, gso).signInIntent
                startActivityForResult(signInIntent, externalProviderViewModel.RC_SIGN_IN)
            }

            btnSignIn.setOnClickListener {
               viewModel.checkError(binding,this@LoginActivity)
            }
            btnFacebook.setOnClickListener {

                externalProviderViewModel.facebookAuth(this@LoginActivity, callbackManager)
            }
            userData = viewModel
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == externalProviderViewModel.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                externalProviderViewModel.firebaseAuthWithGoogle(account.idToken!!, this)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }





}