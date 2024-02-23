package com.sudo_pacman.asaxiybooks.data.source

import android.content.Context
import android.content.SharedPreferences
import com.sudo_pacman.asaxiybooks.data.model.UserData

object MySharedPreference {
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("Uzum_Market", Context.MODE_PRIVATE)
    }

    fun setUserData(userData: UserData) {
        sharedPreferences.edit().putString("id", userData.id).apply()
        sharedPreferences.edit().putString("name", userData.name).apply()
        sharedPreferences.edit().putString("gmail", userData.gmail).apply()
        sharedPreferences.edit().putString("password", userData.password).apply()
        sharedPreferences.edit().putBoolean("login", true).apply()
    }

    fun getUserData(): UserData {
        val id = sharedPreferences.getString("id", "") ?: ""
        val name = sharedPreferences.getString("name", "") ?: ""
        val gmail = sharedPreferences.getString("gmail", "") ?: ""
        val password = sharedPreferences.getString("password", "") ?: ""
        return UserData(id, name, gmail, password)
    }
}