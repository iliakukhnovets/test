package com.example.test_bitnet.data.repository

import com.example.test_bitnet.data.network.NetworkClient

class CurrenciesRepository(
    private val networkClient: NetworkClient
) {

    suspend fun getAllCurrencies() = networkClient.currencyApi.getAllCurrencies()

    suspend fun getTodayRates() = networkClient.currencyApi.getTodayRates()

    suspend fun getRatesByDate(onDate: String) = networkClient.currencyApi.getRatesByDate(onDate)
}