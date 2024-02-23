package com.sudo_pacman.asaxiybooks.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenReadBinding
import com.sudo_pacman.asaxiybooks.presenter.viewModel.ReadViewModel
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.ReadViewModelImpl
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {
    private val binding by viewBinding(ScreenReadBinding::bind)
    private val navArgs by navArgs<ReadScreenArgs>()
    private val viewModel: ReadViewModel by viewModels<ReadViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.downloadBook(navArgs.bookData)
        binding.progress.visibility = View.VISIBLE


        viewModel.bookSharedFlow.onEach {
            "screen kitob keldi name: ${it.name} , path: ${it.absolutePath}".myLog()
//            "screen da bunaqa kitob bormi ${i}"
            binding.pdfViewer.recycle()
            binding.progress.visibility = View.GONE
            binding.pdfViewer
                .fromFile(it)
                .load()
        }.launchIn(lifecycleScope)
    }
}