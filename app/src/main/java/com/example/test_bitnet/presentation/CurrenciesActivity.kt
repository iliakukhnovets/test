package com.example.test_bitnet.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_bitnet.data.database.DataBaseClient
import com.example.test_bitnet.data.network.NetworkClient
import com.example.test_bitnet.data.network.Status
import com.example.test_bitnet.databinding.CurrenciesActivityBinding

class CurrenciesActivity : AppCompatActivity() {

    /**
     * По собесу
     * 1. В коллекциях есть пробелы, разберись как работают и почему
     * 2. Doze mode разберись что и почему там проиходит
     * 3. Многопоточность Java
     * 4. Разберись с жизненным циклом вью
     * Были еще какие-то мелкие недочеты, но если честно, то я уже их не помню. В целом для джуна довольно неплохо)
     */


    private lateinit var viewModel: CurrencyViewModel
    private lateinit var adapter: CurrenciesAdapter
    private lateinit var binding: CurrenciesActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CurrenciesActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                NetworkClient,
                DataBaseClient.getInstance(applicationContext)
            )
        ).get(CurrencyViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CurrenciesAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getCurrencies().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { currencies -> retrieveList(currencies) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(currencyItems: List<CurrencyItem>) {
        adapter.apply {
            addCurrencyItems(currencyItems)
            notifyDataSetChanged()
        }
    }
}