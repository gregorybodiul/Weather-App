package com.exemple.myweatherapp.util

import android.content.Context
import com.google.gson.Gson

class AccountGson(val city: String)

object Account {
    var city: String = "Киев"

    fun saveData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val toJson = (gson.toJson(AccountGson(city))).toString()
        editor.putString("data", toJson)
        editor.apply()
    }

    fun getData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE)
        val cartMemory = sharedPreferences.getString("data", "")
        if (cartMemory != "") {
            val fromJson = Gson().fromJson(cartMemory, AccountGson::class.java)
            city = fromJson.city
        }
    }
}