package com.example.test_bitnet.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.test_bitnet.data.database.RoomDataBaseClient
import com.example.test_bitnet.data.database.entity.CurrencyEntity
import com.example.test_bitnet.data.network.NetworkClient
import com.example.test_bitnet.data.network.response.CurrencyResponse
import com.example.test_bitnet.databinding.CurrenciesScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyFragment : Fragment() {

    private lateinit var binding: CurrenciesScreenBinding
    private val loadErrorText = "Ошибка получения валют"
    private var listOfCurrency = mutableListOf<CurrencyResponse>()
    private var listOfCurrencyResponse = mutableListOf<CurrencyResponse>()
    private var listOfCurrencyCommon = mutableListOf<CurrencyResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrenciesScreenBinding.inflate(inflater, container, false)
        checkInternetConnection()
        loadCurrency()
        loadCurrencyResponse()
        return binding.root
    }

    private fun checkInternetConnection(){
        val connectionManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            Toast.makeText(context, "Network Available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadCurrency() {
        NetworkClient.createCurrencyApi().getAllCurrencies().enqueue(
            object : Callback<MutableList<CurrencyResponse>> {
                override fun onResponse(
                    call: Call<MutableList<CurrencyResponse>>,
                    response: Response<MutableList<CurrencyResponse>>
                ) {
                    if (response.isSuccessful && response.body() != null)
                        getCurrency(response)
                }

                override fun onFailure(call: Call<MutableList<CurrencyResponse>>, t: Throwable) {
                    showMessage(loadErrorText)
                }
            }
        )
    }

    private fun getCurrency(response: Response<MutableList<CurrencyResponse>>): MutableList<CurrencyResponse> {
        val mutableList = response.body()
        if (mutableList != null) {
            listOfCurrency = mutableList
            listOfCurrencyCommon.addAll(listOfCurrency)
        }
        return listOfCurrency
    }

    private fun loadCurrencyResponse() {
        NetworkClient.createCurrencyApi().getAllRates().enqueue(
            object : Callback<MutableList<CurrencyResponse>> {
                override fun onResponse(
                    call: Call<MutableList<CurrencyResponse>>,
                    response: Response<MutableList<CurrencyResponse>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        getCurrencyResponse(response)
                    }
                }

                override fun onFailure(call: Call<MutableList<CurrencyResponse>>, t: Throwable) {
                    showMessage(loadErrorText)
                }
            }
        )
    }

    private fun getCurrencyResponse(response: Response<MutableList<CurrencyResponse>>): MutableList<CurrencyResponse> {
        val mutableList = response.body()
        if (mutableList != null) {
            listOfCurrencyResponse = mutableList
            listOfCurrencyCommon.addAll(listOfCurrencyResponse)
            listOfCurrencyCommon.retainAll(listOfCurrencyResponse)
            listOfCurrency.removeAll(listOfCurrencyResponse)
            for (i in listOfCurrencyCommon.indices)
                if (listOfCurrencyCommon[i].nameEnglish == null) {
                    for (k in listOfCurrency.indices) {
                        if (listOfCurrencyCommon[i].name == listOfCurrency[k].name) {
                            listOfCurrencyCommon[i].nameEnglish = listOfCurrency[k].nameEnglish
                            break
                        }
                    }
                }
            setRecyclerViewAdapter(listOfCurrencyCommon)
            initializeDataBase()
        }
        return listOfCurrencyCommon
    }

    private fun initializeDataBase() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataBase = context?.let {
                Room.databaseBuilder(it, RoomDataBaseClient::class.java, "database-currency")
                    .build()
            }
            val dataBaseDao = dataBase?.currencyDao()
            val list = listOfCurrencyCommon
            dataBaseDao?.insertAllData(list as MutableList<CurrencyEntity>)
        }
    }

    private fun setRecyclerViewAdapter(listOfCurrency: MutableList<CurrencyResponse>) {
        binding.currenciesScreenRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = RecyclerViewAdapter(context, listOfCurrency)
        binding.currenciesScreenRecyclerView.adapter = adapter
    }


    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
