package com.example.androidproject.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.UserDao
import com.example.androidproject.viewModel.ConnexionViewModel

class ConnexionViewModelFactory (
    private val dataSource: UserDao,
    private val application: Application,
    private val userID: Long = 0L // userID
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnexionViewModel::class.java)) {
            return ConnexionViewModel(dataSource, application,userID) as T //
            userID
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}