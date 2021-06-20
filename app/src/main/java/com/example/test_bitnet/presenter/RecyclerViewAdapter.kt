package com.example.test_bitnet.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bitnet.R
import com.example.test_bitnet.data.network.response.CurrencyResponse

class RecyclerViewAdapter(
    private val context: Context?,
    private val listOfData: List<CurrencyResponse>
) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.currencies_screen_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val model = listOfData[position]
        holder.binding.currencyCode.text = model.abbreviation
        holder.binding.currencyItemNameRussian.text = model.name
        holder.binding.currencyItemNameEnglish.text = model.nameEnglish
        holder.binding.currencyItemExchangeRateCurrent.text = model.rate
        holder.binding.currencyItemExchangeRatePast.text = model.scale
    }

    override fun getItemCount(): Int {
        return listOfData.size
    }
}