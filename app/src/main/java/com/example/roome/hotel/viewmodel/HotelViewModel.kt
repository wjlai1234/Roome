package com.example.roome.hotel.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roome.hotel.HotelPopularActivity
import com.example.roome.databinding.ActivityExploreHotelBinding
import com.example.roome.databinding.ActivityPopularBinding
import com.example.roome.databinding.FragmentTripsBinding
import com.example.roome.databinding.HotelFragmentBinding
import com.example.roome.hotel.*
import com.example.roome.hotel.adapter.HotelBestDealAdapter
import com.example.roome.hotel.adapter.HotelLastSearchAdapter
import com.example.roome.hotel.adapter.HotelPopularDestinationsAdapter
import com.example.roome.hotel.adapter.HotelSearchByResultAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HotelViewModel : ViewModel() {
    private var firestore = FirebaseFirestore.getInstance()
    private var bestDealsArrayList: ArrayList<Hotel> = arrayListOf()
    var popularDestinationArrayList: ArrayList<PopularArea> = arrayListOf()
    var lastSearchList: ArrayList<Hotel> = arrayListOf()
    private lateinit var bookData: HashMap<String, Any?>
    var authUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var temArrayList: ArrayList<Hotel> = arrayListOf()
    private var newArrayList: ArrayList<Hotel> = arrayListOf()
    private var newPopularArrayList: ArrayList<Hotel> = arrayListOf()
    private var popularList: ArrayList<Hotel> = arrayListOf()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        authUser.value = Firebase.auth.currentUser
    }


    fun loadData(binding: HotelFragmentBinding, fragment: FragmentActivity, owner: LifecycleOwner) {
        firestore.collection("bestDeals").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "add Failed", e)
                return@addSnapshotListener
            }

            for (dc: DocumentChange in snapshot?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    bestDealsArrayList.add(dc.document.toObject(Hotel::class.java))
                }
            }
            binding.rvBestDeals.apply {
                layoutManager =
                    LinearLayoutManager(
                        fragment,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = HotelBestDealAdapter(bestDealsArrayList)
            }
        }

        firestore.collection("lastSearch").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "add Failed", e)
                return@addSnapshotListener
            }

            for (dc: DocumentChange in snapshot?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    lastSearchList.add(dc.document.toObject(Hotel::class.java))
                }
            }
            binding.apply {
                rvLastSearch.apply {
                    layoutManager =
                        LinearLayoutManager(
                            fragment,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    adapter = HotelLastSearchAdapter(bestDealsArrayList)
                    authUser.value = Firebase.auth.currentUser
                    authUser.observe(owner, Observer {
                        var auth: FirebaseAuth = Firebase.auth
                        if (auth.currentUser != null) {
                            visibility = View.VISIBLE
                            tvLastSearch.visibility = View.VISIBLE
                        } else {
                            visibility = View.GONE
                            tvLastSearch.visibility = View.GONE

                        }
                    })

                }

            }

        }

        firestore.collection("popularDestination").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "add Failed", e)
                return@addSnapshotListener
            }

            for (dc: DocumentChange in snapshot?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {  //
                    popularDestinationArrayList.add(dc.document.toObject(PopularArea::class.java))
                }
            }
            binding.rvPopularDestination.apply {
                layoutManager =
                    LinearLayoutManager(
                        fragment,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = HotelPopularDestinationsAdapter(popularDestinationArrayList)
            }
        }
    }

    fun load(
        binding: ActivityExploreHotelBinding,
        activity: HotelExploreActivity,
        bookData: Book?
    ) {
        firestore.collection("bestDeals").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "add Failed", e)
                return@addSnapshotListener
            }

            for (dc: DocumentChange in snapshot?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    newArrayList.add(dc.document.toObject(Hotel::class.java))
                }
            }
            binding.rvSearchByResult.apply {
                layoutManager =
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                temArrayList.addAll(newArrayList)
                adapter = HotelSearchByResultAdapter(temArrayList)
            }

            temArrayList.clear()
            if (bookData?.searchText.toString().isNotEmpty()) {
                newArrayList.forEach {
                    if (it.title?.lowercase()
                            ?.contains(bookData?.searchText.toString()) == true || it.state?.lowercase()
                            ?.contains(bookData?.searchText.toString()) == true || it.country?.lowercase()
                            ?.contains(bookData?.searchText.toString()) == true
                    ) {
                        temArrayList.add(it)
                    }
                }
                binding.apply {
                    //  rvSearchByResult.adapter!!.notifyDataSetChanged()
                    tvSearchResult.text =
                        String.format("%s hotels found", temArrayList.size)
                }

            }
        }


    }

    fun search(
        binding: ActivityExploreHotelBinding,
        activity: HotelExploreActivity,
    ) {

        binding.apply {

            tiSearch.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {

                    override fun onQueryTextChange(newText: String?): Boolean {
                        temArrayList.clear()
                        val searchText = newText!!.lowercase()
                        if (searchText.isNotEmpty()) {
                            newArrayList.forEach {
                                if (it.title?.lowercase()
                                        ?.contains(searchText) == true || it.state?.lowercase()
                                        ?.contains(searchText) == true || it.country?.lowercase()
                                        ?.contains(searchText) == true
                                ) {
                                    temArrayList.add(it)
                                }
                            }
                            binding.apply {
                                rvSearchByResult.apply {
                                    layoutManager =
                                        LinearLayoutManager(
                                            activity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    adapter = HotelSearchByResultAdapter(temArrayList)
                                }
                                tvSearchResult.text =
                                    String.format("%s hotels found", temArrayList.size)
                            }

                        } else {
                            temArrayList.clear()
                            temArrayList.addAll(newArrayList)
                            binding.apply {
                                rvSearchByResult.apply {
                                    layoutManager =
                                        LinearLayoutManager(
                                            activity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    adapter = HotelSearchByResultAdapter(temArrayList)
                                }
                                tvSearchResult.text =
                                    String.format("%s hotels found", temArrayList.size)
                            }

                        }
                        return false
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }


                })
            }
        }
    }

    fun loadPopular(
        binding: ActivityPopularBinding,
        activity: HotelPopularActivity,
        popularArea: PopularArea
    ) {
        firestore.collection("bestDeals").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "add Failed", e)
                return@addSnapshotListener
            }

            for (dc: DocumentChange in snapshot?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    newPopularArrayList.add(dc.document.toObject(Hotel::class.java))
                }
            }
            binding.rvPopularDestination.apply {
                newPopularArrayList.forEach {
                    if (
                        it.state?.lowercase()?.contains(popularArea?.state!!.lowercase()) == true
                        || it.country?.lowercase()?.contains(popularArea?.country!!.lowercase()) == true
                    ) {
                        popularList.add(it)
                    }
                }

                layoutManager =
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = HotelSearchByResultAdapter(popularList)
                binding.apply {
                    rvPopularDestination.adapter!!.notifyDataSetChanged()
                    tvSearchResult.text =
                        String.format("%s hotels found", popularList.size)
                }
            }


        }
    }

    fun saveBestDealsHotel(hotel:Hotel?,activity: HotelDetailsActivity) {
        bookData = hashMapOf(
            "id" to hotel?.id,
            "title" to hotel?.title,
            "imgPath" to hotel?.imgPath,
            "state" to hotel?.state,
            "country" to hotel?.country,
            "distancePerKmToCity" to hotel?.distancePerKmToCity,
            "rating" to hotel?.rating,
            "pricePerNight" to hotel?.pricePerNight,
            "room" to hotel?.room,
            "adult" to hotel?.adult,
            "startDate" to hotel?.startDate,
            "endDate" to hotel?.endDate,
        )
        var authId =auth.currentUser?.uid.toString()
        var docIDProduct = hotel?.id.toString()
            firestore.collection("Users").document(authId).collection("myTrips").document(docIDProduct).set(bookData).addOnCompleteListener {
                Toast.makeText(activity,"add data successfully", Toast.LENGTH_SHORT).show()
                Log.d("xxxxxxxx", "add data successfully")
            }.addOnFailureListener {
                Toast.makeText(activity,"add data failed", Toast.LENGTH_SHORT).show()
                Log.d("xxxxxxxx", "add data failed")
            }
    }

    fun savePopularHotel() {
        var popularData: HashMap<String, Any?> = hashMapOf(
            "id" to "",
            "state" to "Kuala Lumpur",
            "country" to "Malaysia",
        )
        firestore.collection("popularDestination").document().set(popularData)
            .addOnCompleteListener {
                Log.d("xxxxxxxx", "add data successfully")
            }.addOnFailureListener {
                Log.d("xxxxxxxx", "add data failed")
            }
    }

        fun loadMyTrips(binding: FragmentTripsBinding, fragment: FragmentActivity) {
            firestore.collection("Users").document(auth.currentUser?.uid.toString()).collection("myTrips").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "add Failed", e)
                    return@addSnapshotListener
                }

                for (dc: DocumentChange in snapshot?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bestDealsArrayList.add(dc.document.toObject(Hotel::class.java))
                    }
                }
                binding.rvPopularDestination.apply {
                    layoutManager =
                        LinearLayoutManager(
                            fragment,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    adapter = HotelBestDealAdapter(bestDealsArrayList)
                }
            }

        }


}