package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Event.Event
import com.example.androidproject.MyApi
import com.example.androidproject.database.LocalisationDao
import com.example.androidproject.model.Localisation
import kotlinx.coroutines.*

class ListLocalisationViewModel(
    val database: LocalisationDao,
    application: Application,
    private val choice: Long = 0L
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _localisations = MutableLiveData<List<Localisation>>()
    val localisations: LiveData<List<Localisation>>
        get() = _localisations

    private val _response = MutableLiveData<List<Localisation>>()

    val response: LiveData<List<Localisation>>
        get() = _response

    private val _idLocalisation = MutableLiveData<Long>()

    val idLocalisation: LiveData<Long>
        get() = _idLocalisation

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        Log.i("ListLocalisViewModel", "created")
        initializeLocalisations()
    }

    private fun initializeLocalisations() {
        uiScope.launch {
            var getPropertiesDeferred = MyApi.retrofitService.getLocalisation()
            try {
                var listResult = getPropertiesDeferred.await()
                _response.value = listResult

                for (i in 0..listResult.size) {
                    var localisation = Localisation()
                    localisation.id = _response.value?.get(i)!!.id
                    localisation.latitude = _response.value?.get(i)!!.latitude
                    localisation.longitude = _response.value?.get(i)!!.longitude
                    localisation.date = _response.value?.get(i)!!.date
                    if (database.get(localisation.id) == null) {
                        database.insert(localisation)
                    }
                    if (choice == 0L) _localisations.value = database.getLast3Localisation()
                    else _localisations.value = database.getLocalisations()
                    if (listResult.size == 0) statusMessage.value = Event("Vous n'êtes pas connecté à l'API !")

                }
            } catch (e: Exception) {
                 if (database.getLast3Localisation().size == 0) statusMessage.value = Event("Vous n'êtes pas connecté à l'API !")
                _response.value = ArrayList()
            }
        }
    }

    public fun onRafraichir() {
        _localisations.value = ArrayList()
        initializeLocalisations()
    }

    fun doneNavigating() {
        _idLocalisation.value = null
    }

    fun getLastId() {
        uiScope.launch {
            var getPropertiesDeferred = MyApi.retrofitService.getLastLocation()
            try {
                var listResult = getPropertiesDeferred.await()
                _idLocalisation.value = listResult.id
            } catch (e: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ListLocalisViewModel", "destroyed")
        viewModelJob.cancel()
    }
}