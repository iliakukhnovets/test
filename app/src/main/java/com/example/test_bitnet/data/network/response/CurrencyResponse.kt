package com.example.test_bitnet.data.network.response

import com.squareup.moshi.Json

data class CurrencyResponse(
    @field:Json(name = "Cur_Abbreviation")
    val abbreviation: String,
    @field:Json(name = "Cur_Name")
    val name: String,
    @field:Json(name = "Cur_Name_Eng")
    var nameEnglish:String,
    @field:Json(name = "Cur_OfficialRate")
    var rate:String,
    @field:Json(name = "Cur_Scale")
    val scale:String
)
