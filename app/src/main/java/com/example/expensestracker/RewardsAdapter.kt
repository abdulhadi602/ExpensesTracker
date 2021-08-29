package com.example.skyjobtest.Activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.Repositories.Entitiy.Item


class ItemsAdapter(con: Context) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    var itemList: List<Item> = ArrayList()
    var con: Context

    init {

        this.con = con
    }
    fun setrewardsList(list : List<Item>){
        this.itemList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_format, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp = itemList[position]



            holder.itemName.text = temp.itemName
            holder.itemPrice.text = temp.itemPrice.toString()


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView
        var itemPrice: TextView


        init {

            this.itemName = itemView.findViewById(R.id.itemName)
            this.itemPrice = itemView.findViewById(R.id.itemPrice)

        }


    }


}