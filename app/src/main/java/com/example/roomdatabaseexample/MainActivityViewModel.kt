package com.example.roomdatabaseexample

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(app : Application) : AndroidViewModel(app) {

    private val contactLiveData = MutableLiveData<List<ContactData>>()

    fun getContactLiveDataObserver() : MutableLiveData<List<ContactData>> {
        return contactLiveData
    }

    fun addUser(contactData : ContactData){

        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(getApplication()).contactDao()
            contactDao.insertContact(contactData)
            getAllContact()
        }

    }

    fun getAllContact(){

        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(getApplication()).contactDao()
            val list = contactDao.getContact()
            contactLiveData.postValue(list)
        }

    }

    fun updateContact(contactData: ContactData){
        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(getApplication()).contactDao()
            contactDao.updateContact(contactData)
            getAllContact()
        }

    }

    fun deleteContact(contactData: ContactData){

        GlobalScope.launch {
            val contactDao = ContactDatabase.getDatabase(getApplication()).contactDao()
            contactDao.deleteContact(contactData)
            getAllContact()
        }

    }

}




















