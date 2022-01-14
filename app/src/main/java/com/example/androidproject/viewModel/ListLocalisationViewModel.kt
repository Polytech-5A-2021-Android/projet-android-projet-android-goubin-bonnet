package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.model.Localisation
import kotlinx.coroutines.*

class ListLocalisationViewModel(
    val database: LocalisationDao,
    application: Application,
    private val localisationId: Long = 0L
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _localisations = MutableLiveData<List<Localisation>>()
    val localisations: LiveData<List<Localisation>>
        get() = _localisations

    init {
        Log.i("ListLocalisViewModel", "created")
        initializeLocalisations()
    }

    private fun initializeLocalisations() {
        uiScope.launch {
            _localisations.value = getLocalisationsFromDatabse()
        }
    }

    private suspend fun getLocalisationsFromDatabse(): List<Localisation>? {
        return withContext(Dispatchers.IO) {
            var localisation1 = Localisation()
            localisation1.latitude = 45
            localisation1.longitude = 45
            localisation1.id = insert(localisation1)
            var localisation2 = Localisation()
            localisation2.latitude = 50
            localisation2.longitude = 50
            localisation2.id = insert(localisation2)
            var localisation3 = Localisation()
            localisation3.latitude = 45.729566.toLong()
            localisation3.longitude = 4.827962.toLong()
            localisation3.id = insert(localisation3)
            var localisation4 = Localisation()
            localisation4.latitude = 70
            localisation4.longitude = 70
            localisation4.id = insert(localisation4)

            var localisation = database.getLast3Localisation()
            localisation
        }
    }


    private suspend fun get3LastLocalisationsFromDatabse(): List<Localisation>? {
        return withContext(Dispatchers.IO) {

            var localisation = database.getLast3Localisation()
            localisation
        }
    }

    private suspend fun insert(localisation: Localisation): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(localisation)
        }
        return id
    }

    private suspend fun get(id: Long): Localisation? {
        return withContext(Dispatchers.IO) {
            var localisationn = database.getLastLocalisation()
            localisationn
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ListViewModel", "destroyed")
        viewModelJob.cancel()
    }
}