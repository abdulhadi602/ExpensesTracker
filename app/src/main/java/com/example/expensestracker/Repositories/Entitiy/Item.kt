package com.example.expensestracker.Repositories.Entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expensestracker.Repositories.DateConverter
import java.util.*

@Entity(tableName = "items_table")


 class Item{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var itemName : String
    var itemPrice : Double = 0.0
    @TypeConverters(DateConverter::class)
    var date : Date = Date()
    constructor(itemName: String, itemPrice: Double){
          this.itemName = itemName
        this.itemPrice = itemPrice
    }


}