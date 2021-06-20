package com.example.test_bitnet.data.repository

import com.example.test_bitnet.data.database.DataBaseClient
import com.example.test_bitnet.data.database.model.CurrencyModel
import com.example.test_bitnet.data.network.NetworkClient
import com.example.test_bitnet.presentation.CurrencyItem

class CurrenciesRepository(
    private val networkClient: NetworkClient,
    private val dbClient: DataBaseClient
) {

    suspend fun getAllCurrencies() = networkClient.currencyApi.getAllCurrencies()

    suspend fun getTodayRates() = networkClient.currencyApi.getTodayRates()

    suspend fun getRatesByDate(onDate: String) = networkClient.currencyApi.getRatesByDate(onDate)

    suspend fun getCachedCurrency() = dbClient.currencyDao().fetchAll()

    suspend fun updateCurrencyCache(currencyItem: List<CurrencyItem>) {
        val currencyModels = mutableListOf<CurrencyModel>()
        currencyItem.forEach { item: CurrencyItem ->
            currencyModels.add(
                CurrencyModel(
                    curId = item.curId,
                    code = item.abbreviation,
                    nameEn = item.nameEn,
                    nameRu = item.nameRu,
                    scale = item.scale,
                    rate = item.rate,
                    dynamics = item.currencyDynamics
                )
            )
        }
        dbClient.currencyDao().insertAll(currencyModels)
    }
}