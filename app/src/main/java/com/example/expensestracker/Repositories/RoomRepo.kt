package com.example.expensestracker.Repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.expensestracker.DateUtils
import com.example.expensestracker.Repositories.Entitiy.Item

import com.example.expensestracker.Repositories.Interface.ItemDao
import com.example.expensestracker.Repositories.Interface.Repo
import com.example.expensestracker.Repositories.Singleton.RoomDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RoomRepo(context: Context) : Repo {
    var itemDao : ItemDao
    init {
        itemDao = RoomDataBase.getInstance(context).getItemDAO()
    }
    override fun addItem(item: Item): LiveData<List<Item>> {
       return  object : LiveData<List<Item>>(){
           override fun onActive() {
               super.onActive()
               CoroutineScope(Dispatchers.IO).launch {
                   itemDao.InsertItem(item)
                   val result = itemDao.getItems()
                   withContext(Main){
                       value = result
                   }

               }
           }

       }

    }

    override fun getAllItems(): LiveData<List<Item>> {
       return object : LiveData<List<Item>>(){
           override fun onActive() {
               super.onActive()
               CoroutineScope(Dispatchers.IO).launch {
                   val result = itemDao.getItems()
                   withContext(Main){
                       value = result
                   }
               }
           }

       }
    }

    override fun getTodaysItems(): LiveData<List<Item>> {
        return object : LiveData<List<Item>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(Dispatchers.IO).launch {
                    val result = itemDao.getTodaysItems(DateUtils.getStartOfDayInMillis(),DateUtils.getEndOfDayInMillis())
                    withContext(Main){
                        value = result
                    }

                }
            }
        }
    }

    override fun deleteAllItems() :LiveData<List<Item>>{


        return object : LiveData<List<Item>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(Dispatchers.IO).launch {
                  val job1 = launch {
                      itemDao.deleteItems()
                  }
                    job1.join()
                    val result = itemDao.getItems()
                    withContext(Main){
                        value = result
                    }
                }
            }

        }



    }


}

