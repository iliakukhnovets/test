package com.example.test_bitnet.data.network.response

import com.squareup.moshi.Json

data class CurrencyResponse(
    @field:Json(name = "Cur_Abbreviation")
    val abbreviation: String,
    @field:Json(name = "Cur_Name")
    val nameRu: String,
    @field:Json(name = "Cur_Name_Eng")
    var nameEn: String,
    @field:Json(name = "Cur_ID")
    val curId: Int,
)