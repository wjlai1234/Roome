package com.example.roome.hotel

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roome.R
import com.example.roome.databinding.HotelFragmentBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class HotelFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: HotelFragmentBinding
    private lateinit var hotelViewModel: HotelViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.hotel_fragment,
            container,
            false
        )

        hotelViewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
//        hotelViewModel.endDate.observe(viewLifecycleOwner, Observer {
//            endDate -> binding.
//        })
//        btn.setOnClickListener{
//            myValue.name.value="John"
//            myValue.age.value=8
//        }
//        myValue = ViewModelProvider(this).get(MyData::class.java)
//        myValue.name.observe(this, Observer{
//                newName-> tv.text = newName
//        })
        binding.rvBestDeals.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.rvBestDeals.adapter = HotelBestDealAdapter(allHotelsList)

        binding.rvLastSearch.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLastSearch.adapter = HotelLastSearchAdapter(allHotelsList)


        binding.rvPopularDestination.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularDestination.adapter = HotelPopularDestinationsAdapter(allHotelsList)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.apply {
            cvChooseDateTouchArea.setOnClickListener { setupRangePickerDialog() }

        }


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
//        picker.addOnPositiveButtonClickListener {
//            datePicked ->
//            val startDate =
//        }
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
 open class ExampleThread : Thread() {
     override fun run() {
         super.run()


     }
}