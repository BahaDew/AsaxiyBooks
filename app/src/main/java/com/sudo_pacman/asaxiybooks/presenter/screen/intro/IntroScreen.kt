package com.sudo_pacman.asaxiybooks.presenter.screen.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenIntroBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.IntroAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroScreen : Fragment(R.layout.screen_intro) {


    private val binding by viewBinding(ScreenIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = IntroAdapter(requireActivity()) // Pass FragmentActivity instead of Fragment

        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.viewPager)

        binding.next.setOnClickListener {
            findNavController().navigate(IntroScreenDirections.actionIntroScreenToRegisterScreen())
        }


    }

}