package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageBooksBinding
import com.sudo_pacman.asaxiybooks.presenter.screen.main.MainScreenDirections

class BooksPage : Fragment(R.layout.page_books) {
    private val binding by viewBinding(PageBooksBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            MainScreenDirections.actionMainScreenToInfoScreen()
        }
    }
}