package com.example.roomdatabaseexample

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact")
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val phone : String,
    val age : Int
)
