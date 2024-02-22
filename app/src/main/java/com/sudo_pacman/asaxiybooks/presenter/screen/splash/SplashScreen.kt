package com.sudo_pacman.asaxiybooks.presenter.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val binding by viewBinding(ScreenSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            delay(2000)

            findNavController().navigate(SplashScreenDirections.actionSplashScreenToIntroScreen())
        }


        val animationView = binding.animationView
        animationView.setAnimation("asaxiy_logo.json")
        animationView.playAnimation()

    }

}