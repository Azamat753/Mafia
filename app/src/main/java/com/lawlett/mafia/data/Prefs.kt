package com.lawlett.mafia.data

import android.content.Context
import android.content.SharedPreferences

class Prefs(context:Context){

    private val TOKEN = "token"
    val DEFAULT_VALUE = "defValue"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setToken(token:String){
        sharedPreferences.edit().putString(TOKEN,token).apply()
    }

    val getToken : String
        get() = sharedPreferences.getString(TOKEN,DEFAULT_VALUE).toString()
}