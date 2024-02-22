package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageAudioBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.AudioOuterAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.AudioPageVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.AudioPageVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AudioPage : Fragment(R.layout.page_audio) {
    private val binding by viewBinding(PageAudioBinding::bind)
    private val adapter = AudioOuterAdapter()
    private val viewModel : AudioPageVM by viewModels<AudioPageVMImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }
    private fun initFlow() = binding.apply {
        viewModel.progressSate
            .onEach { progress.isGone = it }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
        viewModel.allCategoryByData
            .onEach { adapter.submitList(it) }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getAllCategoryByData()
        adapter.setOnClickBook {
            viewModel.onClickBook(it)
        }
        adapter.setOnClickCategory {
            viewModel.onClickCategory(it)
        }

    }
}