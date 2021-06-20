package com.example.test_bitnet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test_bitnet.data.database.dao.CurrencyDao
import com.example.test_bitnet.data.database.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class RoomDataBaseClient : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        var instance: RoomDataBaseClient? = null

        fun getInstance(context: Context): RoomDataBaseClient? {
            when (instance) {
                null -> Room.databaseBuilder(context, RoomDataBaseClient::class.java, "appDataBase")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}