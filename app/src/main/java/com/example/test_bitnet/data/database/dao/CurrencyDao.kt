package com.example.test_bitnet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_bitnet.data.database.model.CurrencyModel

@Dao
interface CurrencyDao {
    @Query("SELECT*FROM currencies")
    suspend fun fetchAll(): List<CurrencyModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(model: List<CurrencyModel>)
}