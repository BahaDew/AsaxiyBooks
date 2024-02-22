package com.sudo_pacman.asaxiybooks.presenter.screen.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenLibraryBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.LibraryAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LibraryVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.LibraryVMImp

class LibraryScreen : Fragment(R.layout.screen_library){

    private val binding by viewBinding(ScreenLibraryBinding::bind)
    private val viewModel: LibraryVM by viewModels<LibraryVMImp>()
    private val adapter = LibraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}