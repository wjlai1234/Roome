package com.example.roome.splash

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.HotelFragmentBinding
import com.example.roome.databinding.SplashActivityBinding
import com.example.roome.hotel.viewmodel.AuthViewModel
import com.example.roome.hotel.viewmodel.HotelViewModel
import com.example.roome.onboard.OnboardActivity
import com.example.roome.user.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val authViewModel by lazy { ViewModelProvider(this).get(AuthViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        if (auth.currentUser != null) {
            authViewModel.authUser.value = auth.currentUser
            Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" +  auth.currentUser)
            Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" +  authViewModel.authUser.value)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        var binding: SplashActivityBinding = DataBindingUtil.setContentView(
            this,
            R.layout.splash_activity
        )

        binding.apply {
            btnGetStart.setOnClickListener {
             //   hotelViewModel.savePopularHotel()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            tvLogin.setOnClickListener {
                startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
            }
        }
    }
}