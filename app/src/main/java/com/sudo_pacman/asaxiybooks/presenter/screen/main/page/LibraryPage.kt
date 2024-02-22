package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageLibraryBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.LibraryAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LibraryVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.LibraryVMImp

class LibraryPage : Fragment(R.layout.page_library){

    private val binding by viewBinding(PageLibraryBinding::bind)
    private val viewModel: LibraryVM by viewModels<LibraryVMImp>()
    private val adapter = LibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}