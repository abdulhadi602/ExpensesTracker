package com.example.expensestracker.Repositories.Entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expensestracker.Repositories.DateConverter
import java.util.*

@Entity(tableName = "items_table")


 class Item(var itemName: String, var itemPrice: Double) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    @TypeConverters(DateConverter::class)
    var date : Date = Date()
}