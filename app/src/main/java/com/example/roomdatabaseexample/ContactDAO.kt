package com.example.roomdatabaseexample

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertContact(contactData : ContactData)

    @Update
    fun updateContact(contactData: ContactData)

    @Delete
    fun deleteContact(contactData: ContactData)

    @Query("SELECT * FROM contact ORDER BY id DESC")
    suspend fun getContact() : List<ContactData>

}