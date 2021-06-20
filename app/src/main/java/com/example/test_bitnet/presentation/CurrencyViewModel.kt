package com.example.test_bitnet.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.test_bitnet.data.database.DataBaseClient
import com.example.test_bitnet.data.database.model.CurrencyModel
import com.example.test_bitnet.data.network.NetworkClient
import com.example.test_bitnet.data.network.Resource
import com.example.test_bitnet.data.network.response.CurrencyRateResponse
import com.example.test_bitnet.data.network.response.CurrencyResponse
import com.example.test_bitnet.data.repository.CurrenciesRepository
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(
    application: Application,
    private val currenciesRepository: CurrenciesRepository
) :
    AndroidViewModel(application) {

    companion object {
        const val ONE_MONTH: Long = 2592000000
    }

    fun getCurrencies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        if (isOnline()) {
            try {
                // TODO: let's imagine that these lists will be always have the same size
                val allCurrencies = currenciesRepository.getAllCurrencies()
                val ratesOfPreviousMonth = currenciesRepository.getRatesByDate(getCurrentDate())
                val todayRates = currenciesRepository.getTodayRates()
                val currenciesItems = mutableListOf<CurrencyItem>()

                todayRates.forEach { currencyRate: CurrencyRateResponse ->
                    currenciesItems.add(
                        CurrencyItem(
                            abbreviation = currencyRate.abbreviation,
                            curId = currencyRate.curId,
                            rate = currencyRate.rate,
                            scale = currencyRate.scale
                        )
                    )
                    allCurrencies.forEach { currency: CurrencyResponse ->
                        currenciesItems.forEach { currencyItem: CurrencyItem ->
                            if (
                                currencyItem.curId == currency.curId
                            ) {
                                currencyItem.nameRu = currency.nameRu
                                currencyItem.nameEn = currency.nameEn
                            }
                        }
                    }

                    ratesOfPreviousMonth.forEach { previousRate: CurrencyRateResponse ->
                        currenciesItems.forEach { currencyItem: CurrencyItem ->
                            if (
                                currencyItem.curId == previousRate.curId
                            ) {
                                when {
                                    currencyItem.rate > previousRate.rate -> currencyItem.currencyDynamics =
                                        CurrencyDynamics.INCREASED
                                    currencyItem.rate < previousRate.rate -> currencyItem.currencyDynamics =
                                        CurrencyDynamics.DECREASED
                                    currencyItem.rate == previousRate.rate -> currencyItem.currencyDynamics =
                                        CurrencyDynamics.NO_CHANGES
                                }
                            }
                        }
                    }
                }

                currenciesRepository.updateCurrencyCache(currenciesItems)
                emit(Resource.success(data = currenciesItems))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        } else {
            try {
                val currenciesItems = mutableListOf<CurrencyItem>()
                val currencies = currenciesRepository.getCachedCurrency()
                currencies.forEach { model: CurrencyModel ->
                    currenciesItems.add(
                        CurrencyItem(
                            abbreviation = model.code,
                            curId = model.curId,
                            rate = model.rate,
                            scale = model.scale,
                            nameRu = model.nameRu,
                            nameEn = model.nameEn,
                            currencyDynamics = model.dynamics
                        )
                    )
                }

                emit(Resource.success(data = currenciesItems))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = cal.timeInMillis - ONE_MONTH
        val dateTime = cal.time
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return dateFormat.format(dateTime)
    }

    private fun isOnline(): Boolean {
        val connMgr =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}

data class CurrencyItem(
    var abbreviation: String = "",
    var nameRu: String = "",
    var nameEn: String = "",
    var curId: Int = 0,
    var rate: Double = 0.0,
    var scale: Int = 0,
    var currencyDynamics: CurrencyDynamics = CurrencyDynamics.NO_CHANGES
)

class ViewModelFactory(
    private val application: Application,
    private val networkClient: NetworkClient,
    private val dbClient: DataBaseClient
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(
                application,
                CurrenciesRepository(networkClient, dbClient)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}