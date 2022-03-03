package com.example.roome.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roome.R
import com.example.roome.databinding.HotelFragmentBinding
import com.example.roome.databinding.SplashActivityBinding
import com.example.roome.onboard.OnboardActivity


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.splash_activity
        )
        binding.btnGetStart.setOnClickListener {
            startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
        }
    }

}