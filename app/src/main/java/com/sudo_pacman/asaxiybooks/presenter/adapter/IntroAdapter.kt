package com.sudo_pacman.asaxiybooks.presenter.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sudo_pacman.asaxiybooks.presenter.screen.intro.page.IntroPage

class IntroAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = IntroPage().apply {
        arguments = bundleOf(Pair("POS", position))
    }
}