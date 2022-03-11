package com.example.roome.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roome.R
import com.example.roome.databinding.ActivityLoginBinding
import com.example.roome.databinding.ActivitySignUpBinding
import com.example.roome.databinding.SplashActivityBinding
import com.example.roome.hotel.viewmodel.AuthViewModel

class SignUpActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(AuthViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding: ActivitySignUpBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_sign_up
        )
        binding.apply {
            tvSignIn.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
            btnSignUp.setOnClickListener {
                customAuth(this)
            }
        }
    }

    private fun customAuth(binding: ActivitySignUpBinding) {
        var email: String = binding.etEmail4.text.toString().trim()
        var password: String = binding.etPassword3.text.toString().trim()
        var userName: String = binding.etFullName.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.register(email, password, userName,this)


        }
    }
}