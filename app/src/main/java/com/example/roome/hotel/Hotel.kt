package com.example.roome.hotel

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.roome.BR
data class Hotel(
    var id: String? = null,
    var title: String? = null,
    var imgPath: String? = null,
    var state: String? = null,
    var country: String? = null,
    var distancePerKmToCity: Double? = null,
    var rating: Int? = null,
    var pricePerNight: Int? = null,
    var room: Int? = null,
    var adult: Int? = null,
    var startDate: Int? = null,
    var endDate: Int? = null
) : Parcelable, BaseObservable() {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(imgPath)
        parcel.writeString(state)
        parcel.writeString(country)
        distancePerKmToCity?.let { parcel.writeDouble(it) }
        rating?.let { parcel.writeInt(it) }
        pricePerNight?.let { parcel.writeInt(it) }
        room?.let { parcel.writeInt(it) }
        adult?.let { parcel.writeInt(it) }
        startDate?.let { parcel.writeInt(it) }
        endDate?.let { parcel.writeInt(it) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hotel> {
        override fun createFromParcel(parcel: Parcel): Hotel {
            return Hotel(parcel)
        }

        override fun newArray(size: Int): Array<Hotel?> {
            return arrayOfNulls(size)
        }
    }

    @get:Bindable
    var hotelImage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hotelImage)
        }
}


