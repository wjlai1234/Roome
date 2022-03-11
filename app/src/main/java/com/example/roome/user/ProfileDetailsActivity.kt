package com.example.roome.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.roome.R
import com.example.roome.databinding.ActivityLoginBinding
import com.example.roome.databinding.ActivityProfileDetailsBinding

class ProfileDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityProfileDetailsBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_profile_details
        )
    }
}