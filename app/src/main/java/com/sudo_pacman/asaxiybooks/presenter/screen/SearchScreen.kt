package com.sudo_pacman.asaxiybooks.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenSearchBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.SearchAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.SearchVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.SearchVMImp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchScreen: Fragment(R.layout.screen_search) {
    private val viewModel : SearchVM by viewModels<SearchVMImp>()
    private val binding by viewBinding(ScreenSearchBinding::bind)
    private val adapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initFlow()
    }
    private fun initView() = binding.apply {
        rvSearch.adapter = adapter
        rvSearch.layoutManager = LinearLayoutManager(requireContext())
        searchBack.setOnClickListener {
            viewModel.onClickBack()
        }
        search.addTextChangedListener {
            viewModel.textChange(search.text.toString())
        }
        adapter.setOnClickBook {
            viewModel.onClickBook(it)
        }
    }
    private fun initFlow() = binding.apply {
        viewModel.resultBooks
            .onEach {
                adapter.submitList(it)
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

}