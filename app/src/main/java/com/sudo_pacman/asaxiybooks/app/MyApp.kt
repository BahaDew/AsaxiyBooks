package com.sudo_pacman.asaxiybooks.app

import android.app.Application
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
    }

}