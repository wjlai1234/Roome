package com.example.roome

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.roome.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
//                R.id.nav_splashFragment->setCurrentFragment(firstFragment)
//                R.id.nav_onboardFragment->setCurrentFragment(secondFragment)
//                R.id.settings->setCurrentFragment(thirdFragment)
            }
            true
        }

        setContentView(binding.root)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }
}

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            Log.d("xxxxxxMain",destination.id.toString())
//            Log.d("xxxxxxMain",R.id.nav_splashFragment.toString())
//            Log.d("xxxxxxMain",R.id.nav_onboardFragment.toString())
//            binding.bottomNavigationView.visibility =
//
//                when (destination.id) {
//                    R.id.nav_splashFragment -> {
//                        View.GONE
//                    }
//                    R.id.nav_onboardFragment -> {
//                        View.GONE
//                    }
//                    else -> {
//                        View.VISIBLE
//                    }
//                }
//        }