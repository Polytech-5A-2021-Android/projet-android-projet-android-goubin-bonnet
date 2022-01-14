package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.model.Localisation
import kotlinx.coroutines.*

class LocalisationViewModel (
    val database: LocalisationDao,
    application: Application,
    private val localisationId: Long = 0L
) : AndroidViewModel(application)
{
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _localisation = MutableLiveData<Localisation>()
    val localisation: LiveData<Localisation>
        get() = _localisation

    init {
        Log.i("LocalisationViewModel", "created")
        initializeLocalisation()
    }

    private fun initializeLocalisation() {
        _localisation.value = database.get(localisationId)
    }

    private suspend fun get(): Localisation {
        return withContext(Dispatchers.IO) {
            var localisation = database.get(localisationId)
            localisation
        }
    }

}