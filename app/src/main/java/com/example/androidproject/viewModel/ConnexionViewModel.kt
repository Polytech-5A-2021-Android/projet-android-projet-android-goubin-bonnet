package com.example.androidproject.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidproject.Event.Event
import com.example.androidproject.database.UserDao
import com.example.androidproject.model.User
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ConnexionViewModel (
    val database: UserDao,
    application: Application,
    private val userID: Long = 0L // userID
) : AndroidViewModel(application)
{

    var pays = arrayListOf<String>("France", "Espagne", "Italie", "Kosovo")

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user
    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage


    private val _navigateToPersonalDataFragment = MutableLiveData<User>()


    private val _navigateToConnexionActivity = MutableLiveData<User>()


    val navigateToConnexionActivity: LiveData<User>
        get() = _navigateToConnexionActivity


    fun doneValidateNavigating() {
        _navigateToConnexionActivity.value = null
    }

    val navigateToPersonalDataFragment: LiveData<User>
        get() = _navigateToPersonalDataFragment


    fun doneNavigating() {
        _navigateToPersonalDataFragment.value = null
    }

    fun onValidateIdentity() {
        uiScope.launch {
            val user = user.value ?: return@launch
            if(user.firstname.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Username")
                return@launch
            }
            if(user.password.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Password")
                return@launch
            }
            val userLogin = getByLogin(user.firstname, user.password)
            if (userLogin?.firstname.isNullOrEmpty()) {
                statusMessage.value = Event("Identifiant ou mot de passe incorrect")
                return@launch
            }
            _navigateToPersonalDataFragment.value = userLogin

        }
    }

    fun onValidateCreation() {
        uiScope.launch {
            val user = user.value ?: return@launch
            if(user.firstname.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Username")
                return@launch
            }
            if(user.lastname.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Lastname")
                return@launch
            }
            if(user.password.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Password")
                return@launch
            }
            if(user.email.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer un Email")
                return@launch
            }
            if(user.adresse.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer une Adresse")
                return@launch
            }
            if(user.ville.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez indiquer une Ville")
                return@launch
            }
            if(user.gender.isNullOrEmpty()) {
                statusMessage.value = Event("Vous devez selectionez un Genre")
                return@launch
            }
            update(user)
            _navigateToConnexionActivity.value = user
        }
    }


    init {
        Log.i("IdentityViewModel", "created")
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDatabase()
        }
    }
    private suspend fun getUserFromDatabase(): User? {
        return withContext(Dispatchers.IO) {
            var user = database.get(userID) // userID
            if (user == null) {
                user = User()
                user.id = insert(user)
                var monUser = User(user.id + 1, "Fabien", "Fabien", "a", 1, "Fabien")
                insert(monUser)
            }
            user
        }
    }

    private suspend fun insert(user: User): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.insert(user)
        }
        return id
    }

    private suspend fun update(user: User) {
        withContext(Dispatchers.IO) {
            database.update(user)
        }
    }
    private suspend fun get(id: Long) {
        withContext(Dispatchers.IO) {
            database.get(id)
        }
    }

    private suspend fun getLast(): User? {
        return withContext(Dispatchers.IO) {
            database.getLastUser()
        }
    }

    private suspend fun getByLogin(username: String?, password: String?): User? {
        return withContext(Dispatchers.IO) {

            var user = database.getByLogin(username, password)
            if (user == null) {
                user =  User()
            }
            user
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("IdentityViewModel", "destroyed")
        viewModelJob.cancel()
    }

    fun onGender(gender: String) {
        _user.value?.gender = gender
    }

    fun onAge(age: Long) {
        _user.value?.age = age
    }

    fun onBirthday(birthday: String) {
        val f = SimpleDateFormat("dd/MM/yyyy")
        val d = f.parse(birthday)
        _user.value?.birthdayDate = d.time
    }

    fun onPays(position : Int) {
        _user.value?.pays = pays.get(position)

    }

    fun getAge() : String {
        return "Test"
    }


}