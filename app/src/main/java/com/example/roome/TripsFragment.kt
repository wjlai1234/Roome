package com.example.roome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.roome.databinding.FragmentTripsBinding
import com.example.roome.hotel.viewmodel.HotelViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class TripsFragment : Fragment() {
    private lateinit var trpsBinding: FragmentTripsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trpsBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_trips,
            container,
            false
        )

        return trpsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        auth = Firebase.auth
        if (auth.currentUser != null) {
            hotelViewModel.loadMyTrips(trpsBinding ,requireActivity())
        }else {
            Toast.makeText(requireContext(),"Pls Log in", Toast.LENGTH_SHORT).show()
        }


    }

}