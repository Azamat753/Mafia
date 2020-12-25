package com.lawlett.mafia.data.repository

import android.content.Context
import android.content.SharedPreferences

class Prefs(context:Context){

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

}