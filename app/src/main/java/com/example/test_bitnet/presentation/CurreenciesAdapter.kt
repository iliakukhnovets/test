package com.example.test_bitnet.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bitnet.R
import com.example.test_bitnet.databinding.CurrenciesItemViewBinding

class CurrenciesAdapter(private val currencies: ArrayList<CurrencyItem>) :
    RecyclerView.Adapter<CurrenciesAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CurrenciesItemViewBinding.bind(itemView)

        fun bind(currencyItem: CurrencyItem) {
            binding.currencyItemCode.text = currencyItem.abbreviation
            binding.currencyItemNameRussian.text = currencyItem.nameRu
            binding.currencyItemNameEnglish.text = currencyItem.nameEn
            binding.currencyItemExchangeCurrentRate.text = currencyItem.rate.toString()
            binding.currencyItemScale.text = currencyItem.scale.toString()

            binding.currencyItemExchangeCurrentRate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    getDynamicsColor(currencyItem)
                )
            )
        }

        private fun getDynamicsColor(currencyItem: CurrencyItem): Int =
            when (currencyItem.currencyDynamics) {
                CurrencyDynamics.INCREASED -> R.color.currency_green
                CurrencyDynamics.DECREASED -> R.color.currency_red
                CurrencyDynamics.NO_CHANGES -> R.color.currency_gray
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currencies_item_view, parent, false)
        )

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    fun addCurrencyItems(currencyItems: List<CurrencyItem>) {
        this.currencies.apply {
            clear()
            addAll(currencyItems)
        }
    }
}