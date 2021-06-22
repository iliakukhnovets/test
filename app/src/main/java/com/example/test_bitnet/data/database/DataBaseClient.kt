package com.example.test_bitnet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.test_bitnet.data.database.dao.CurrencyDao
import com.example.test_bitnet.data.database.model.CurrencyModel

@Database(entities = [CurrencyModel::class], version = 1, exportSchema = false)
@TypeConverters(DynamicsConverter::class)
abstract class DataBaseClient : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {

        @Volatile
        private var INSTANCE: DataBaseClient? = null

        // Нужна двойная нал проверка
        //        fun getInstance(context: Context): DataBaseClient  {
        //            return INSTANCE ?: synchronized(this) {
        //                INSTANCE ?: buildDatabase(context).also {
        //                    INSTANCE = it
        //                }
        //            }
        //        }
        // Тип так
        fun getInstance(context: Context): DataBaseClient {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseClient::class.java,
                    "currencies_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}