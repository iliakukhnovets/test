package com.example.test_bitnet.data.database

import androidx.room.TypeConverter
import com.example.test_bitnet.presentation.CurrencyDynamics

class DynamicsConverter {

    @TypeConverter
    fun fromPriority(priority: CurrencyDynamics): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): CurrencyDynamics {
        return CurrencyDynamics.valueOf(priority)
    }
}