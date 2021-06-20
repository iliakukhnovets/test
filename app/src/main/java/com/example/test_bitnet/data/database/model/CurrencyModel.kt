package com.example.test_bitnet.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_bitnet.presentation.CurrencyDynamics

@Entity(tableName = "currencies")
data class CurrencyModel(
    @PrimaryKey(autoGenerate = false) val curId: Int,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "name_ru") val nameRu: String,
    @ColumnInfo(name = "name_en") val nameEn: String,
    @ColumnInfo(name = "official_rate") val rate: Double,
    @ColumnInfo(name = "scale") val scale: Int,
    @ColumnInfo(name = "dynamics") val dynamics: CurrencyDynamics,
)