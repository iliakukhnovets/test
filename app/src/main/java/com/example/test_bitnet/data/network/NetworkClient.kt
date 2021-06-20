package com.example.test_bitnet.data.network

import com.example.test_bitnet.BuildConfig
import com.example.test_bitnet.data.network.api.CurrencyApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkClient {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val currencyApi: CurrencyApi = getRetrofit().create(CurrencyApi::class.java)
}