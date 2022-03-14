package com.example.roome.hotel

import android.os.Parcel
import android.os.Parcelable

data class Book(
    var id: String? = null,
    var searchText: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var room: Int? = null,
    var adult: Int? = null,
    var child: Int? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(searchText)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        room?.let { parcel.writeInt(it) }
        adult?.let { parcel.writeInt(it) }
        child?.let { parcel.writeInt(it) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}