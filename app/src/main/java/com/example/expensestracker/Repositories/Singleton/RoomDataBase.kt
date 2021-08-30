package com.example.expensestracker.Repositories.Singleton

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Repositories.Interface.ItemDao

@Database(entities = arrayOf(Item::class), version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun getItemDAO(): ItemDao
    companion object{
        private var INSTANCE : RoomDataBase? = null

        fun getInstance(context: Context) : RoomDataBase {
            if(INSTANCE != null){
                return INSTANCE as RoomDataBase
            }
            val dbInstance = Room.databaseBuilder(
                context,
                RoomDataBase::class.java,
                "items_table"
            ).build()
            INSTANCE = dbInstance
            return INSTANCE as RoomDataBase
        }

    }


}
