package com.example.test_bitnet.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.test_bitnet.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setNavigationComponent()
    }

    private fun setNavigationComponent() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navigationController = navHostFragment.navController
        navigationController.navigate(R.id.currencyFragment)
    }
}

//TODO запросы не приносят нужные данные, совмещение 2-ух запросов и вывод результата в адаптер
//TODO отображение оффлайн
//TODO no action bar theme