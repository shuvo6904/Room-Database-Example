package com.example.roomdatabaseexample

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.example.roomdatabaseexample.databinding.ContactEditDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ContactAdapter.RawClickListener {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainActivityViewModel by lazy { MainActivityViewModel(application) }
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

        mainViewModel.getAllContact()
    }

    private fun setRecyclerView() {
        binding.roomRecyclerId.apply {

            layoutManager = LinearLayoutManager(this@MainActivity)
            contactAdapter = ContactAdapter(this@MainActivity)
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
            mainViewModel.addUser(data)
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


    override fun onDeleteUserClickListener(contactData: ContactData) {
        mainViewModel.deleteContact(contactData)
        Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onItemClickListener(contactData: ContactData) {
        Toast.makeText(this, "Hello ${contactData.name}", Toast.LENGTH_LONG).show()
    }

    override fun onEditUserClickListener(contactData: ContactData) {
        showEditDialog(contactData)
    }

    private fun showEditDialog(contactData: ContactData) {
        val dialog = Dialog(this)
        val dialogBinding = ContactEditDialogBinding.inflate(LayoutInflater.from(this),null,false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setData(dialogBinding,contactData)

        dialogBinding.buttonUpdate.setOnClickListener {

            val name = dialogBinding.edtName.text.toString()
            val phone = dialogBinding.edtPhone.text.toString()
            val age = dialogBinding.edtAge.text.toString()

            if (validateEdt(dialogBinding.edtName,name,"Enter Your Name")
                && validateEdt(dialogBinding.edtPhone,phone,"Enter Your Phone Number")
                && validateEdt(dialogBinding.edtAge,age,"Enter Your Age")
            ){
                val data = ContactData(contactData.id,name,phone,age.toInt())
                mainViewModel.updateContact(data)
                Toast.makeText(this,"Contact Updated",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()

    }

    private fun setData(dialogBinding: ContactEditDialogBinding, contactData: ContactData) {
        dialogBinding.edtName.setText(contactData.name)
        dialogBinding.edtPhone.setText(contactData.phone)
        dialogBinding.edtAge.setText(contactData.age.toString())

    }


}



















