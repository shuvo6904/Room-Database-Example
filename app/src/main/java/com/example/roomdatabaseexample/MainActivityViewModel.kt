package com.example.roomdatabaseexample

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    /*lateinit var contactLiveData : MutableLiveData<List<ContactData>>

    init {
        contactLiveData = MutableLiveData<List<ContactData>>()
    }*/

    private val contactLiveData = MutableLiveData<List<ContactData>>()

    fun getContactLiveDataObserver() : MutableLiveData<List<ContactData>> {
        return contactLiveData
    }

    fun addUser(context: Context, contactData : ContactData){

        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(context).contactDao()
            contactDao.insertContact(contactData)
            getAllContact(context)
        }

    }

    fun getAllContact(context: Context){

        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(context).contactDao()
            val list = contactDao.getContact()
            contactLiveData.postValue(list)
        }

    }
}