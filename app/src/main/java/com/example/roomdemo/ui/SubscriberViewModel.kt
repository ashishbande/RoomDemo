package com.example.roomdemo.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.database.Subscriber
import com.example.roomdemo.repository.SubscriberRepository
import com.example.roomdemo.utils.CoroutineContextProvider
import com.example.roomdemo.utils.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SubscriberViewModel(private val repository: SubscriberRepository,val contextProvider: CoroutineContextProvider): ViewModel(), Observable{
    val subscriber = repository.subscribers




    private var isUpdateDelete = false
    private lateinit var subscriberUpdateDelete: Subscriber

    val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
       get() = statusMessage


    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.postValue("Save")
        clearOrDeleteButtonText.postValue("Clear All")
    }

    fun saveOrUpdate(){
        if (isUpdateDelete) {
               subscriberUpdateDelete.name = inputName.value!!
               subscriberUpdateDelete.email = inputEmail.value!!
               update(subscriberUpdateDelete)
        }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0,name, email))
            inputName.value= null
            inputEmail.value = null
        }


       }

    fun initUpdateDelete(subscriber: Subscriber){
        inputName.value =subscriber.name
        inputEmail.value = subscriber.email
        isUpdateDelete = true
        subscriberUpdateDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    fun clearAllOrDelete(){
        if (isUpdateDelete){
            delete(subscriberUpdateDelete)
        }else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(contextProvider.IO) {
        val newRowid = repository.insert(subscriber)
        if (newRowid >= -1) {
            statusMessage.value = Event("Inserted Successfully")
        } else {
            statusMessage.value = Event("Error occoured")
        }
    }
    fun update(subscriber: Subscriber) = viewModelScope.launch {
        val noRowsUpdated = repository.update(subscriber)
        inputName.value =null
        inputEmail.value = null
        isUpdateDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"

        statusMessage.value = Event("$noRowsUpdated Row Updated Successfully")
    }
    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value =null
        inputEmail.value = null
        isUpdateDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Deleted Successfully")
    }
    fun deleteAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        statusMessage.value = Event("$noOfRowsDeleted Deleted Successfully")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    suspend fun getPages(): String {
        delay(10000)
        return "pages"
    }

}