package com.sudo_pacman.asaxiybooks.presenter.page

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
import com.sudo_pacman.asaxiybooks.databinding.PageTabBooksBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.CategoryPdfByBooksAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.TabBooksPageVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.TabBooksPageVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TabBooksPage : Fragment(R.layout.page_tab_books) {
    private val binding by viewBinding(PageTabBooksBinding::bind)
    private val adapter = CategoryPdfByBooksAdapter()
    private val viewModel: TabBooksPageVM by viewModels<TabBooksPageVMImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(requireContext())
        adapter.onClickBook { viewModel.onClickBook(it) }
        viewModel.requestAllDownloadBooks()
        addBook.setOnClickListener {
            viewModel.onClickAddBook()
        }
    }

    private fun initFlow() = binding.apply {
        viewModel.allDownloadBooks
            .onEach {
                adapter.submitList(it)
            }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
        viewModel.placeholderState
            .onEach {
                placeholder.isGone = it
            }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestAllDownloadBooks()
    }
}