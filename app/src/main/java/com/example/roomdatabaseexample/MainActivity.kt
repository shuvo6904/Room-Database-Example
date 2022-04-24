package com.example.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabaseexample.adapter.ContactAdapter
import com.example.roomdatabaseexample.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainActivityViewModel by lazy { MainActivityViewModel(this) }
    //lateinit var database: ContactDatabase
    lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setRecyclerView()
        getData()
        setUpObserver()

        //database = ContactDatabase.getDatabase(this)

        binding.buttonSubmitId.setOnClickListener {
            saveDataToDatabase()
        }

        /*GlobalScope.launch {
            database.contactDao().insertContact(ContactData(0, "Shuvo", "01630209460", 23))
        }*/
    }

    private fun setUpObserver() {

        mainViewModel.getContactLiveDataObserver().observe(this, Observer {
            contactAdapter.setListData(ArrayList(it))
            contactAdapter.notifyDataSetChanged()
        })

    }

    private fun getData() {

        mainViewModel.getAllContact(this)
    }

    private fun setRecyclerView() {
        binding.roomRecyclerId.apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            contactAdapter = ContactAdapter()
            adapter = contactAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }


    }

    private fun saveDataToDatabase() {

        val name = binding.editTextNameId.text.toString()
        val phone = binding.editTextPhoneId.text.toString()
        val age = binding.editTextAgeId.text.toString()

        if (validateEdt(binding.editTextNameId, name, "Enter Your Name")
            && validateEdt(binding.editTextPhoneId, phone, "Enter Your Phone Number")
            && validateEdt(binding.editTextAgeId, age, "Enter Your Age")){

            val data = ContactData(0, name, phone, age.toInt())
            mainViewModel.addUser(this,data)
            clearEdt()
            Toast.makeText(this,"User Added",Toast.LENGTH_SHORT).show()

        }



    }

    private fun clearEdt() {
        binding.editTextNameId.setText("")
        binding.editTextPhoneId.setText("")
        binding.editTextAgeId.setText("")
    }

    private fun validateEdt(edt: EditText, value: String, warningMessage: String): Boolean{
        if (value.isEmpty()){
            edt.requestFocus()
            Toast.makeText(this,warningMessage, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    /*fun getData(view: View) {
        database.contactDao().getContact().observe(this, Observer {

            Log.d("shuvo", it.toString())
        })

    }*/
}