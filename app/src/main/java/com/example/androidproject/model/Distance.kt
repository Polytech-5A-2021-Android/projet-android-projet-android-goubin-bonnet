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
@Entity(tableName = "distance")
data class Distance (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var _id: Long = 0L,

    @ColumnInfo(name = "distance")
    private var _distance: Float = 0.0F
    ) : Parcelable,
    BaseObservable() {
    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    var distance: Float
        @Bindable get() = _distance
        set(value) {
            _distance = value
            notifyPropertyChanged(BR.distance)
        }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeFloat(distance)
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