package com.sudo_pacman.asaxiybooks.presenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.navigation.AppNavigationHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var appNavigationHandler: AppNavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        appNavigationHandler
            .buffer
            .onEach {
                it.invoke(navController)
            }.launchIn(lifecycleScope)
    }
}