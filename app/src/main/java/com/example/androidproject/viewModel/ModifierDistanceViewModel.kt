package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Event.Event
import com.example.androidproject.database.DistanceDao
import com.example.androidproject.model.Distance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ModifierDistanceViewModel(

    val database: DistanceDao,
    application: Application,
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _distance = MutableLiveData<Distance>()
    val distance: LiveData<Distance>
        get() = _distance

    private val _distance2 = MutableLiveData<Distance>()
    val distance2: LiveData<Distance>
        get() = _distance2

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        Log.i("DistanceViewModel", "created")
        var newDistance = Distance()
        if (database.getLastDistance() == null) {
            newDistance.distance = 13.5F
            newDistance.id = database.insert(newDistance)
        }
        _distance.value = database.getLastDistance()
    }


    fun onValidate(valueDistance: Float) {
        var oldDistance = database.getLastDistance()
        oldDistance?.distance = valueDistance
        oldDistance?.let {
            database.update(it)
            statusMessage.value = Event("Distance modifié avec succés!")
            _distance.value = database.getLastDistance()
        }
    }

}