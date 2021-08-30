package com.example.expensestracker.Repositories.Entitiy

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(date: Long): Date? {
        return Date(date)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long {
        return date?.time ?: Calendar.getInstance().timeInMillis
    }
}