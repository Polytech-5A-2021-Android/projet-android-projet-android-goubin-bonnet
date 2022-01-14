package com.example.androidproject.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.InverseMethod
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import com.example.androidproject.BR
import java.util.*

@Keep
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var _id: Long = 0L,

    @ColumnInfo(name = "lastname")
    private var _lastname: String? = "",

    @ColumnInfo(name = "firstname")
    private var _firstname: String? = "",

    @ColumnInfo(name = "email")
    private var _email: String? = "",

    @ColumnInfo(name = "age")
    private var _age: Long? = 0,

    @ColumnInfo(name = "password")
    private var _password: String? = "",

    @ColumnInfo(name = "adresse")
    private var _adresse: String? = "",

    @ColumnInfo(name = "ville")
    private var _ville: String? = "",

    @ColumnInfo(name = "pays")
    private var _pays: String? = "France",

    @ColumnInfo(name = "birthday_date")
    private var _birthdayDate: Long = 0,

    @ColumnInfo(name = "gender")
    private var _gender: String? = ""
) : Parcelable,
    BaseObservable() {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }
    var lastname: String?
        @Bindable get() = _lastname
        set(value) {
            _lastname = value
            notifyPropertyChanged(BR.lastname)
        }
    var firstname: String?
        @Bindable get() = _firstname
        set(value) {
            _firstname = value
            notifyPropertyChanged(BR.firstname)
        }
    var birthdayDate: Long
        @Bindable get() = _birthdayDate
        set(value) {
            _birthdayDate = value
            notifyPropertyChanged(BR.birthdayDate)
        }
    var gender: String?
        @Bindable get() = _gender
        set(value) {
            _gender = value
            notifyPropertyChanged(BR.gender)
        }
    var password: String?
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

    var pays: String?
        @Bindable get() = _pays
        set(value) {
            _pays = value
            notifyPropertyChanged(BR.pays)
        }

    var adresse: String?
        @Bindable get() = _adresse
        set(value) {
            _adresse = value
            notifyPropertyChanged(BR.adresse)
        }

    var ville: String?
        @Bindable get() = _ville
        set(value) {
            _ville = value
            notifyPropertyChanged(BR.ville)
        }

    var age: Long?
        @Bindable get() = _age
        set(value) {
            _age = value
            notifyPropertyChanged(BR.age)
        }

    var email: String?
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.email)
        }


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(lastname)
        parcel.writeString(firstname)
        parcel.writeLong(birthdayDate)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}



object LongConverter {
    @JvmStatic
    @InverseMethod("stringToDate")
    fun dateToString(
        value: Long
    ): String {
        val date = Date(value)
        val f = SimpleDateFormat("dd/MM/yy")
        val dateText = f.format(date)
        return dateText
    }

    @JvmStatic
    fun stringToDate(
        value: String
    ): Long {
        val f = SimpleDateFormat("dd/MM/yy")
        val d = f.parse(value)
        return d.time
    }
}