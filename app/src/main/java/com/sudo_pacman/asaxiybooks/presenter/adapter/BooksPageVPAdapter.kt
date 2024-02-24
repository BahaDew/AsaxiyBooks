package com.sudo_pacman.asaxiybooks.presenter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sudo_pacman.asaxiybooks.presenter.page.TabAudioPage
import com.sudo_pacman.asaxiybooks.presenter.page.TabBooksPage

class BooksPageVPAdapter(
    fr: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fr, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            TabBooksPage()
        else
            TabAudioPage()
    }

}