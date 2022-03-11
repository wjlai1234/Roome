package com.example.roome

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roome.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    companion object{
        private lateinit var navController: NavController
        private lateinit var binding: ActivityMainBinding
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_hotel -> {
                    navController.navigate(R.id.nav_hotel)
                }
                R.id.nav_trips -> {
                    navController.navigate(R.id.nav_trips)
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.nav_profile)
                }


            }
            true
        }

        setContentView(binding.root)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }

    private fun setupNavigationMenu(navController: NavController) {

        binding.bottomNavigationView?.setupWithNavController(navController)
    }

}
