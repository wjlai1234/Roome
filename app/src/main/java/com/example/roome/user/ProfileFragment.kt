package com.example.roome.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.roome.MainActivity
import com.example.roome.R
import com.example.roome.databinding.FragmentProfileBinding
import com.example.roome.splash.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )




        return profileBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        profileBinding.apply {
            btnToDetails.setOnClickListener {
                startActivity(Intent(requireContext(), ProfileDetailsActivity::class.java))
            }

            auth = Firebase.auth
            if (auth.currentUser != null) {
                profileLogout.visibility = View.VISIBLE
                profilePassword.visibility = View.VISIBLE
                profileLogout.setOnClickListener {
                    Firebase.auth.signOut()
                   // startActivity(Intent(requireContext(), SplashActivity::class.java))
                    navController.navigate(R.id.action_nav_profile_to_splashActivity)
                 //   activity?.onBackPressed()
                }
            } else{
                profilePassword.visibility = View.GONE
                profileLogout.visibility = View.GONE
            }
        }

    }

}