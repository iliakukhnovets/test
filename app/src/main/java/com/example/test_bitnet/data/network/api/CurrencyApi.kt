package com.example.test_bitnet.data.network.api

import com.example.test_bitnet.data.network.response.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {
    @GET("currencies")
    fun getAllCurrencies(): Call<MutableList<CurrencyResponse>>

    @GET("rates?periodicity=1")
    fun getAllRates(): Call<MutableList<CurrencyResponse>>
}