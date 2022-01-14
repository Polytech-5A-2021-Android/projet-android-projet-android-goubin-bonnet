package com.example.androidproject.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidproject.BR

@Keep
@Entity(tableName = "localisation")
data class Localisation(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var _id: Long = 0L,

    @ColumnInfo(name = "latitude")
    private var _latitude: Long = 0L,

    @ColumnInfo(name = "longitude")
    private var _longitude: Long = 0L
    ) : Parcelable,
    BaseObservable() {
    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    var latitude: Long
        @Bindable get() = _latitude
        set(value) {
            _latitude = value
            notifyPropertyChanged(BR.latitude)
        }

    var longitude: Long
        @Bindable get() = _longitude
        set(value) {
            _longitude = value
            notifyPropertyChanged(BR.longitude)
        }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(latitude)
        parcel.writeLong(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Localisation> {
        override fun createFromParcel(parcel: Parcel): Localisation {
            return Localisation(parcel)
        }

        override fun newArray(size: Int): Array<Localisation?> {
            return arrayOfNulls(size)
        }
    }
}