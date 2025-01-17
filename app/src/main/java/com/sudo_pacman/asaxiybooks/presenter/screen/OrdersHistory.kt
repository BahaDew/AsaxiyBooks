package com.sudo_pacman.asaxiybooks.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenHistoryBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.SearchAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.HistoryVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.HistoryVMImp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class OrdersHistory : Fragment(R.layout.screen_history) {
    private val binding by viewBinding(ScreenHistoryBinding::bind)
    private val viewModel: HistoryVM by viewModels<HistoryVMImp>()

    private val adapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initFlow()
    }

    private fun initViews()= binding.apply{
       rvListOnHistory.adapter = adapter
       rvListOnHistory.layoutManager = LinearLayoutManager(requireContext())

        btnBack.setOnClickListener {
            viewModel.onClickBack()
        }

        adapter.setOnClickBook {
            viewModel.onClickBook(it)
        }
        viewModel.getAllBooks()
    }
    private fun initFlow() = binding.apply {
        viewModel.historyBooks
            .onEach {
                adapter.submitList(it)
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}