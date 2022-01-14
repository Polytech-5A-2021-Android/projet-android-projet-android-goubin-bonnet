package com.example.androidproject.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.viewModel.ListLocalisationViewModel
import com.example.androidproject.viewModel.LocalisationViewModel

class LocalisationViewModelFactory (
    private val dataSource: LocalisationDao,
    private val application: Application,
    private val localisationId: Long = 0L
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalisationViewModel::class.java)) {
            return LocalisationViewModel(dataSource, application, localisationId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
