package com.sudo_pacman.asaxiybooks.presenter.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.databinding.ScreenSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val binding by viewBinding(ScreenSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = MySharedPreference

        lifecycleScope.launch {
            delay(2000)

            if (sharedPref.isLogin()) {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
            } else findNavController().navigate(SplashScreenDirections.actionSplashScreenToIntroScreen())

//            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val animationView = binding.animationView
        animationView.setAnimation("asaxiy_logo.json")
        animationView.playAnimation()
    }

}