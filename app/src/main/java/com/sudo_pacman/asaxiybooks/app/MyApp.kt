package com.sudo_pacman.asaxiybooks.app

import android.app.Application
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryAddBook
import com.sudo_pacman.asaxiybooks.domain.impl.TestViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}