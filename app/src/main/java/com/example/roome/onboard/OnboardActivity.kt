package com.example.roome.onboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.OnboardActivityBinding
import java.util.*


class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: OnboardActivityBinding
    var currentPage = 0
    private val DELAY_MS: Long = 300 //delay in milliseconds before task is to be executed
    private val PERIOD_MS: Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.onboard_activity
        )
        binding.viewPager.adapter = SlideViewPagerAdapter(this)

        binding.btnLogIn.setOnClickListener {
            startActivity(Intent(this@OnboardActivity, MainActivity::class.java))
        }
        val handler: Handler = Handler()
        val update = Runnable {
            if (currentPage === 3 - 1) {
                currentPage = 0
            }
            binding.viewPager.setCurrentItem(currentPage++, true)
        }

        var timer = Timer()// This will create a new Thread
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, (DELAY_MS), (PERIOD_MS))

    }

}