package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Event.Event
import com.example.androidproject.MyApi
import com.example.androidproject.database.DistanceDao
import com.example.androidproject.model.Distance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ModifierDistanceViewModel(

    val database: DistanceDao,
    application: Application,
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _distance = MutableLiveData<Distance>()
    val distance: LiveData<Distance>
        get() = _distance

    private val _done = MutableLiveData<String>()
    val done: LiveData<String>
        get() = _done

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        Log.i("DistanceViewModel", "created")

        uiScope.launch {
            var getPropertiesDeferred = MyApi.retrofitService.getDistance()
            try {
                var listResult = getPropertiesDeferred.await()
                var newDistance = Distance()
                newDistance.id = listResult.id
                newDistance.distance = listResult.distance
                database.insert(newDistance)
                _distance.value = database.getLastDistance()
                _done.value = "Done"
            } catch (e: Exception) {
            }

        }
    }

    fun doneNavigating() {
        _done.value = null
    }

    fun onValidate(valueDistance: Float) {
        var oldDistance = database.getLastDistance()
        oldDistance?.distance = valueDistance
        uiScope.launch {
            var getPropertiesDeferred =
                oldDistance?.let { MyApi.retrofitService.putDistance(it) }
            try {
                var listResult = getPropertiesDeferred?.await()
                var newDistance = Distance()
                newDistance.id = listResult!!.id
                newDistance.distance = listResult.distance
                database.update(newDistance)
                _distance.value = database.getLastDistance()
                _done.value = "Done"
                statusMessage.value = Event("Distance modifié avec succés!")

            } catch (e: Exception) {
            }
        }
        //oldDistance?.let {
        //  database.update(it)
        //statusMessage.value = Event("Distance modifié avec succés!")
        //_distance.value = database.getLastDistance()
        //}
    }

}