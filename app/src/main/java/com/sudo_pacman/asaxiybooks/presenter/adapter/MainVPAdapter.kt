package com.sudo_pacman.asaxiybooks.presenter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sudo_pacman.asaxiybooks.presenter.page.AudioPage
import com.sudo_pacman.asaxiybooks.presenter.page.BooksPage
import com.sudo_pacman.asaxiybooks.presenter.page.LibraryPage
import com.sudo_pacman.asaxiybooks.presenter.page.ProfilePage
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.AudioPageVMImpl

class MainVPAdapter(
    fr: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fr, lifecycle) {
    private val page1 = BooksPage()
    private val page2 = LibraryPage()
    private val page3 = AudioPage()
    private val page4 = ProfilePage()
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> page1
            1 -> page2
            2 -> page3
            else -> page4
        }
    }
}