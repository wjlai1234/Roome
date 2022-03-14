package com.example.roome.hotel

import android.os.Parcel
import android.os.Parcelable

data class PopularArea(var id: String?= null,var imgPath: String?= null, var country: String?= null, var state: String?= null)  :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgPath)
        parcel.writeString(country)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PopularArea> {
        override fun createFromParcel(parcel: Parcel): PopularArea {
            return PopularArea(parcel)
        }

        override fun newArray(size: Int): Array<PopularArea?> {
            return arrayOfNulls(size)
        }
    }
}

