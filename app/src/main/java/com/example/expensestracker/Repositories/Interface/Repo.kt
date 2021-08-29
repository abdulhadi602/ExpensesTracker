package com.example.expensestracker.Repositories.Interface

import androidx.lifecycle.LiveData
import com.example.expensestracker.Repositories.Entitiy.Item
import java.util.*


interface Repo {
    fun addItem(item : Item) : LiveData<List<Item>>
    fun getAllItems(): LiveData<List<Item>>
    fun getTodaysItems() : LiveData<List<Item>>
    fun deleteAllItems() : LiveData<List<Item>>
}