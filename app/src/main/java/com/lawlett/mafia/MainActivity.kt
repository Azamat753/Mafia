package com.lawlett.mafia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.lawlett.mafia.data.Prefs

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        checkForAuthentication()
    }

    private fun checkForAuthentication() {
        if (Prefs(this).getToken == Prefs(this).DEFAULT_VALUE){
            navController.navigate(R.id.navigation_global_to_AuthFragment)
        }
    }
}