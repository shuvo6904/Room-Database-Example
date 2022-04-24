package com.example.roomdatabaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseexample.ContactData
import com.example.roomdatabaseexample.databinding.ItemRoomDataBinding

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    private var item = ArrayList<ContactData>()

    fun setListData(item : ArrayList<ContactData>){
        this.item = item
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRoomDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(item[position])

    }

    override fun getItemCount(): Int {
        return item.size
    }

    class MyViewHolder(private val binding: ItemRoomDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contactData: ContactData){
            binding.textName.text = contactData.name
            binding.textPhone.text = contactData.phone
            binding.textAge.text = contactData.age.toString()
            binding.textId.text = contactData.id.toString()
        }

    }
}