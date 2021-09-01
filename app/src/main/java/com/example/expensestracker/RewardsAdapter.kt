package com.example.skyjobtest.Activities

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
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

        checkisDayChanging(position,holder,temp)
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
    fun checkisDayChanging(pointer : Int,holder : ViewHolder,item : Item){
        var nextItemDate: Date?
        if(pointer<itemList.size-1){
            nextItemDate = itemList[pointer+1].date
            if(nextItemDate isDifferentFrom item.date){
                holder.totalMoneyForDay.visibility = View.VISIBLE

               holder.totalMoneyForDay.text ="Total : ${itemList.findDataByDay(item.date).findDaysTotal()}"

            }else{
                holder.totalMoneyForDay.visibility = View.GONE
            }
        }else{
            holder.totalMoneyForDay.visibility = View.VISIBLE
            holder.totalMoneyForDay.text ="Total : ${itemList.findDataByDay(item.date).findDaysTotal()}"

        }
    }
    fun List<Item>.findDataByDay(date : Date): List<Item>{
        val list : ArrayList<Item> = ArrayList()
        for(item in this){
            if(item.date isSameAs  date ){
              list.add(item)
            }
        }
        return list
    }
    fun List<Item>.findDaysTotal() : Double{
        var total = 0.0
        for(item in this){
            total+=item.itemPrice
        }
        return total
    }
    fun checkDate(pointer : Int,holder : ViewHolder,item : Item){
        val lastItemDate: Date?
        if(pointer>0){
            lastItemDate = itemList[pointer-1].date
            if(lastItemDate isDifferentFrom item.date){
                holder.itemDateLayout.visibility = View.VISIBLE
                holder.itemDate.text = item.date.formatDate()
            }else{
                holder.itemDateLayout.visibility = View.GONE
            }
        }else{
            holder.itemDateLayout.visibility = View.VISIBLE
            holder.itemDate.text = item.date.formatDate()
        }

    }
    infix fun Date.isSameAs(itemDate: Date) : Boolean{
        return this.formatDate().equals(itemDate.formatDate())
    }
    infix fun Date.isDifferentFrom(itemDate: Date) : Boolean{
        return !this.formatDate().equals(itemDate.formatDate())
    }
    fun Date.formatDate() : String{
        val sdf = SimpleDateFormat("dd MMM yyy", Locale.getDefault())
        return sdf.format(this)
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView
        var itemPrice: TextView
        var deleteBtn : Button
        var itemDate : TextView
        var itemDateLayout : LinearLayout
        var totalMoneyForDay : TextView


        init {

            this.itemName = itemView.findViewById(R.id.itemName)
            this.itemPrice = itemView.findViewById(R.id.itemPrice)
            this.deleteBtn = itemView.findViewById(R.id.deleteBtn)
            this.itemDate = itemView.findViewById(R.id.itemDate)
            this.itemDateLayout = itemView.findViewById(R.id.itemDateLayout)
            this.totalMoneyForDay = itemView.findViewById(R.id.totalMoneyForDay)
        }


    }


}