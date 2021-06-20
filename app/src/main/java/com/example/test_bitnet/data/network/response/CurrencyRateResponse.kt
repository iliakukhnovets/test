package com.example.test_bitnet.data.network.response

import com.squareup.moshi.Json

data class CurrencyRateResponse(
    @field:Json(name = "Cur_Abbreviation")
    val abbreviation: String,
    @field:Json(name = "Cur_Name")
    val name: String,
    @field:Json(name = "Cur_OfficialRate")
    var rate: Double,
    @field:Json(name = "Cur_Scale")
    val scale: Int,
    @field:Json(name = "Cur_ID")
    val curId: Int
)
