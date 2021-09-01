package com.example.skyjobtest.Activities

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Utils.AlertState
import com.example.expensestracker.Utils.ItemAlert
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ItemsAdapter(con: Context, handler: Handler) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    var itemList: List<Item> = ArrayList()
    var con: Context
    val handler : Handler
    var lastDateItem: Date? =null
    init {
        this.handler = handler
        this.con = con
    }
    fun setrewardsList(list: List<Item>){
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
        checkDate(position,holder,temp)
        holder.itemName.text = temp.itemName
        holder.itemPrice.text = temp.itemPrice.toString()
        holder.itemView.setOnClickListener {
            ItemAlert(con, handler, AlertState.UPDATE, temp).show()
        }
        holder.deleteBtn.setOnClickListener {
            ItemAlert(con, handler, AlertState.DELETE, temp).show()
        }

    }
    fun checkDate(pointer : Int,holder : ViewHolder,item : Item){
//        if(pointer == 0){
//            lastDateItem == null
//        }
//        if(lastDateItem!= null){
//            if(lastDateItem!! isDateDifferent item.date){
//                holder.itemDate.visibility = View.VISIBLE
//                holder.itemDate.text = item.date.formatDate()
//            }
//        }
        holder.itemDate.visibility = View.VISIBLE
        holder.itemDate.text = item.date.formatDate()
       // lastDateItem = item.date
    }
    infix fun Date.isDateDifferent(itemDate: Date) : Boolean{
        return this.formatDate().equals(itemDate.formatDate())
    }
    fun Date.formatDate() : String{
        val sdf = SimpleDateFormat("dd MMM yyy", Locale.getDefault())
        return sdf.format(this)
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView
        var itemPrice: TextView
        var deleteBtn : FloatingActionButton
        var itemDate : TextView


        init {

            this.itemName = itemView.findViewById(R.id.itemName)
            this.itemPrice = itemView.findViewById(R.id.itemPrice)
            this.deleteBtn = itemView.findViewById(R.id.deleteBtn)
            this.itemDate = itemView.findViewById(R.id.itemDate)
        }


    }


}