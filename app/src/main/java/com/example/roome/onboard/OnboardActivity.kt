package com.example.roome.onboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.OnboardActivityBinding
import com.example.roome.user.LoginActivity
import com.example.roome.user.SignUpActivity
import java.util.*


class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: OnboardActivityBinding
    var currentPage = 0
    private val DELAY_MS: Long = 400 //delay in milliseconds before task is to be executed
    private val PERIOD_MS: Long = 1200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.onboard_activity
        )
        binding.apply {
            viewPager.adapter = SlideViewPagerAdapter(this@OnboardActivity)
            btnLogIn.setOnClickListener {
                startActivity(Intent(this@OnboardActivity, LoginActivity::class.java))
            }
            btnCreateAccount.setOnClickListener {
                startActivity(Intent(this@OnboardActivity, SignUpActivity::class.java))
            }

            val handler: Handler = Handler()
            val update = Runnable {
                if (currentPage === 4 - 1) {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage++, true)
            }
            var timer = Timer()// This will create a new Thread
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, (DELAY_MS), (PERIOD_MS))
        }






    }

}