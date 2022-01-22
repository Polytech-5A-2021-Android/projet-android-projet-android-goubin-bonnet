package com.example.androidproject.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.DistanceDao
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.viewModel.LocalisationViewModel
import com.example.androidproject.viewModel.ModifierDistanceViewModel

class ModifierDistanceViewModelFactory (
    private val dataSource: DistanceDao,
    private val application: Application,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModifierDistanceViewModel::class.java)) {
            return ModifierDistanceViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}