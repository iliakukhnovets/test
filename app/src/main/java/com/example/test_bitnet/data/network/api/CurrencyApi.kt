package com.example.test_bitnet.data.network.api

import com.example.test_bitnet.data.network.response.CurrencyRateResponse
import com.example.test_bitnet.data.network.response.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("currencies")
    suspend fun getAllCurrencies(): List<CurrencyResponse>

    // periodicity Лучше через @query задавать
    @GET("rates?periodicity=0")
    suspend fun getTodayRates(): List<CurrencyRateResponse>

    // Не стоит дублировать метод, если у 2 методов одинаковый функционал
    @GET("rates?periodicity=0")
    suspend fun getRatesByDate(@Query("ondate") onDate: String): List<CurrencyRateResponse>
}