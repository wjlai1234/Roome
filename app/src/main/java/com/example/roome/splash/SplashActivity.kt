package com.example.roome.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.HotelFragmentBinding
import com.example.roome.databinding.SplashActivityBinding
import com.example.roome.onboard.OnboardActivity
import com.example.roome.user.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        if (auth.currentUser != null) {
            // Not signed in, launch the Sign In activity
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
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            tvLogin.setOnClickListener {
                startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
            }
       }
    }
}