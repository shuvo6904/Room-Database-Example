package com.example.roomdatabaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseexample.ContactData
import com.example.roomdatabaseexample.databinding.ItemRoomDataBinding

class ContactAdapter(private val listener : RawClickListener) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    private var item = ArrayList<ContactData>()

    fun setListData(item : ArrayList<ContactData>){
        this.item = item
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRoomDataBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(item[position])

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(item[position])
        }

    }

    override fun getItemCount(): Int {
        return item.size
    }

    class MyViewHolder(
        private val binding: ItemRoomDataBinding,
        private val listener : RawClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contactData: ContactData){
            binding.textName.text = contactData.name
            binding.textPhone.text = contactData.phone
            binding.textAge.text = contactData.age.toString()
            binding.textId.text = contactData.id.toString()

            binding.imageDelete.setOnClickListener{
                listener.onDeleteUserClickListener(contactData)
            }

            binding.imageEdit.setOnClickListener {
                listener.onEditUserClickListener(contactData)
            }
        }

    }

    interface RawClickListener{
        fun onDeleteUserClickListener(contactData: ContactData)
        fun onItemClickListener(contactData: ContactData)
        fun onEditUserClickListener(contactData: ContactData)
    }
}