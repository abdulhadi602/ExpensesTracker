package com.example.skyjobtest.Activities

import android.content.AsyncQueryHandler
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Utils.AlertState
import com.example.expensestracker.Utils.ItemAlert
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ItemsAdapter(con: Context,handler: Handler) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    var itemList: List<Item> = ArrayList()
    var con: Context
    val handler : Handler
    init {
        this.handler = handler
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
        holder.itemView.setOnClickListener {
            ItemAlert(con , handler, AlertState.UPDATE,temp).show()
        }
        holder.deleteBtn.setOnClickListener {
            ItemAlert(con , handler, AlertState.DELETE,temp).show()
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView
        var itemPrice: TextView
        var deleteBtn : FloatingActionButton


        init {

            this.itemName = itemView.findViewById(R.id.itemName)
            this.itemPrice = itemView.findViewById(R.id.itemPrice)
            this.deleteBtn = itemView.findViewById(R.id.deleteBtn)
        }


    }


}