package com.example.test_bitnet.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Cur_Abbreviation") val currencyAbbreviation: String,
    @ColumnInfo(name = "Cur_Name") val name: String,
    @ColumnInfo(name = "Cur_Name_Eng") val nameEnglish: String,
    @ColumnInfo(name = "Cur_OfficialRate") val rate:String,
    @ColumnInfo(name ="Cur_Scale") val scale:String
)