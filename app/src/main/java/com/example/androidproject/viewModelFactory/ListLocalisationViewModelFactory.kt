package com.example.androidproject.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.viewModel.ListLocalisationViewModel

class ListLocalisationViewModelFactory(
    private val dataSource: LocalisationDao,
    private val application: Application,
    private val choice: Long = 0L
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLocalisationViewModel::class.java)) {
            return ListLocalisationViewModel(dataSource, application, choice) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}