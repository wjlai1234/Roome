package com.example.roome.hotel

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roome.R
import com.example.roome.databinding.HotelFragmentBinding
import com.example.roome.databinding.RoomAdultDialogFragmentBinding
import com.example.roome.hotel.adapter.HotelBestDealAdapter
import com.example.roome.hotel.adapter.HotelLastSearchAdapter
import com.example.roome.hotel.adapter.HotelPopularDestinationsAdapter
import com.example.roome.hotel.viewmodel.BookViewModel
import com.example.roome.hotel.viewmodel.HotelViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class HotelFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var hotelBinding: HotelFragmentBinding
    private lateinit var roomAdultChildBinding: RoomAdultDialogFragmentBinding
    private val hotelViewModel by lazy { ViewModelProvider(this).get(HotelViewModel::class.java) }
    private val bookViewModel by lazy { ViewModelProvider(this).get(BookViewModel::class.java) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hotelBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.hotel_fragment,
            container,
            false
        )
        roomAdultChildBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.room_adult_dialog_fragment, null, false
        )



        bookViewModel.apply {
            room.observe(viewLifecycleOwner) { room ->
                roomAdultChildBinding.tvNumberOfRoom.text = room.toString()
                hotelBinding.tvRoom.text = if(room <= 1)  String.format("%s Room",room.toString()) else String.format("%s Rooms",room.toString())
            }
            adult.observe(viewLifecycleOwner) { adult ->
                roomAdultChildBinding.tvNumberOfAdult.text = adult.toString()
                hotelBinding.tvAdult.text = if(adult <= 1)  String.format("%s Adult",adult.toString()) else String.format("%s Adults",adult.toString())
            }
            children.observe(viewLifecycleOwner) { children ->
                roomAdultChildBinding.tvNumberOfChild.text = children.toString()
            }
            endDate.observe(viewLifecycleOwner) { endDate ->
                hotelBinding.tvEndDate.text = endDate.toString()
            }
            startDate.observe(viewLifecycleOwner) { startDate ->
                hotelBinding.tvStartDate.text = startDate.toString()
            }
        }

        hotelBinding.apply {
            rvBestDeals.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = HotelBestDealAdapter(allHotelsList)
            }
            rvLastSearch.apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HotelLastSearchAdapter(allHotelsList)
            }
            rvPopularDestination.apply {
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HotelPopularDestinationsAdapter(allHotelsList)
            }
            cvChooseDateTouchArea.setOnClickListener { setupRangePickerDialog() }


            val builder = AlertDialog.Builder(requireActivity())
            builder.setView(roomAdultChildBinding.root)
            var dialog = builder.create()

            cvChooseNumOfRoomTouchArea.setOnClickListener {
                dialog.show()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                roomAdultChildBinding.apply {
                    book = bookViewModel
                    btnApply.setOnClickListener {
                        bookViewModel.btnApply(dialog)
                    }

                }
            }
        }

        return hotelBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        hotelBinding.btnSearch.setOnClickListener { navController.navigate(R.id.action_nav_hotel_to_nav_explore) }
    }

    private fun setupRangePickerDialog() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Select the Date")
        builder.setCalendarConstraints(limitRange().build())
        val picker = builder.build()
        picker.show(
            parentFragmentManager, picker.toString()
        )
        Log.i("HotelFragment", "Date Range Picker:${picker.selection}")
        picker.addOnPositiveButtonClickListener { datePicked ->
            bookViewModel.startDate.value = convertLongToDate(datePicked.first)
            bookViewModel.endDate.value = convertLongToDate(datePicked.second)
            Log.i(
                "HotelFragment",
                bookViewModel.startDate.value.toString() + " " + bookViewModel.endDate.value.toString()
            )
        }
    }

    private fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
        return format.format(date)
    }

    private fun limitRange(): CalendarConstraints.Builder {

        val constraintsBuilderRange = CalendarConstraints.Builder()

        val calendarStart: Calendar = Calendar.getInstance()
        val calendarEnd: Calendar = Calendar.getInstance()

        val cal = Calendar.getInstance(TimeZone.getTimeZone("Malaysia"))
        val year = cal.get(Calendar.YEAR)
        val startMonth = cal.get(Calendar.MONTH)
        val startDate = cal.get(Calendar.DAY_OF_MONTH)

        val endMonth = cal.get(Calendar.MONTH) + 10
        val endDate = cal.get(Calendar.DAY_OF_MONTH)

        calendarStart.set(year, startMonth, startDate - 1)
        calendarEnd.set(2022, endMonth - 1, endDate)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)

        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))

        return constraintsBuilderRange
    }

    class RangeValidator(private val minDate: Long, private val maxDate: Long) :
        CalendarConstraints.DateValidator {


        constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            TODO("not implemented")
        }

        override fun describeContents(): Int {
            TODO("not implemented")
        }

        override fun isValid(date: Long): Boolean {
            return !(minDate > date || maxDate < date)

        }

        companion object CREATOR : Parcelable.Creator<RangeValidator> {
            override fun createFromParcel(parcel: Parcel): RangeValidator {
                return RangeValidator(parcel)
            }

            override fun newArray(size: Int): Array<RangeValidator?> {
                return arrayOfNulls(size)
            }
        }

    }


}
