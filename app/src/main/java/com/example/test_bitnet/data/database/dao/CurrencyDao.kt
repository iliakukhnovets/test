package com.example.test_bitnet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_bitnet.data.database.entity.CurrencyEntity
import com.example.test_bitnet.data.network.response.CurrencyResponse

@Dao
interface CurrencyDao {
    @Query("SELECT*FROM currencies")
    fun getAllDataBaseCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(entity: MutableList<CurrencyEntity>)
}