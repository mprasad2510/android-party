package com.android.nortontestapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.nortontestapplication.model.AuthToken
import com.android.nortontestapplication.model.LoginRequest
import com.android.nortontestapplication.model.ServersListItem
import com.android.nortontestapplication.repository.ServersRepository
import com.android.nortontestapplication.utils.Event
import com.android.nortontestapplication.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: ServersRepository,
                                                @ApplicationContext private val context: Context
): ViewModel() {

    private val _token = MutableLiveData<AuthToken>()

    val token:LiveData<AuthToken>
    get() = _token

    private val _serversList = MutableLiveData<List<ServersListItem>>()

    val serversList:LiveData<List<ServersListItem>>
    get() = _serversList

    val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
    get() = statusMessage
    val processing = MutableLiveData<Boolean>()

     fun getToken(loginRequest: LoginRequest) = viewModelScope.launch(Dispatchers.IO) {
         try {
             processing.postValue(true)
             val response = repository.getToken(loginRequest)
             processing.postValue(false)
             if (!response.isSuccessful) {
                 statusMessage.value = Event("Failed to Login , Error code:${response.code()}!!")
                 return@launch
             }
             response.body()?.let {
                 if (response.isSuccessful) {
                     _token.postValue(it)
                 } else {
                     statusMessage.value = Event("Unauthorized token")
                 }
             }
         } catch (exception: Exception) {
             statusMessage.value = Event("Error is $exception")
             exception.printStackTrace()
             processing.postValue(false)
         }
//        repository.getToken(loginRequest).let { response ->
//          if (response.isSuccessful) {
//              _token.postValue(response.body())
//          } else {
//              statusMessage.value = Event("Try to Login Again!!")
//          }
//        }
    }

     fun getAllServersList() = viewModelScope.launch(Dispatchers.IO){
         try {
             processing.postValue(true)
             val response = repository.getServersList(
                 AuthToken("Bearer ${SessionManager(context).fetchAuthToken()}"))
             processing.postValue(false)
             if (!response.isSuccessful) {
                 statusMessage.value = Event("Failed to fetch list , Error code:${response.code()}!!")
                 return@launch
             }

             response.body()?.let {
                 if (response.isSuccessful) {
                     _serversList.postValue(it)
                 } else {
                     statusMessage.value = Event("Unable to access servers list")
                 }
             }
         } catch (exception: Exception) {
             statusMessage.value = Event("Error is $exception")
             exception.printStackTrace()
             processing.postValue(false)
         }
//        repository.getServersList(
//            AuthToken("Bearer ${SessionManager(context).fetchAuthToken()}")).let { response ->
//        if (response.isSuccessful) {
//            _serversList.postValue(response.body())
//        } else {
//            statusMessage.value = Event("Error Occurred")
//        }
//
//        }
    }

    fun insertServers(serversListItem: ServersListItem):Job = viewModelScope.launch {
        val newRowId:Long = repository.insert(serversListItem)
        if (newRowId>-1) {
            statusMessage.value = Event("Servers Item inserted successfully")
        } else {
            statusMessage.value = Event("Error Occurred while inserting items")
        }
    }
}