package com.example.expensestracker.Repositories.Interface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expensestracker.Repositories.Entitiy.Item
import java.util.*

@Dao
interface ItemDao{

    @Insert
    suspend fun InsertItem(item : Item)

    @Query("SELECT * FROM items_table ORDER BY id ASC")
    suspend fun getItems(): List<Item>

    @Query("DELETE FROM items_table")
    suspend fun deleteItems()

    @Query("SELECT * from items_table WHERE date BETWEEN :startDate AND :finishDate")
    suspend fun getTodaysItems(startDate: Long,finishDate : Long) : List<Item>

}