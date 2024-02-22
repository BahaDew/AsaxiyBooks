package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.PageBooksBinding
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryImpl
import com.sudo_pacman.asaxiybooks.presenter.screen.main.MainScreenDirections
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BooksPage : Fragment(R.layout.page_books) {
    private val binding by viewBinding(PageBooksBinding::bind)
    private val repository = RepositoryImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStart.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToInfoScreen())
        }

    }
}