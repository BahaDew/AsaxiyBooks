package com.sudo_pacman.asaxiybooks.presenter.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageLibraryBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.LibraryAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LibraryVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.LibraryVMImp
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LibraryPage : Fragment(R.layout.page_library){

    private val binding by viewBinding(PageLibraryBinding::bind)
    private val adapter = LibraryAdapter()
    private val viewModel: LibraryVM by viewModels<LibraryVMImp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllCategoryByData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initFlow()
    }

    private fun initViews() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickBook { bookData ->
            viewModel.onClickBook(bookData)
        }
        adapter.setOnClickCategory { categoryName ->
            viewModel.onClickCategory(categoryName)
        }
    }


    private fun initFlow() = binding.apply {
        viewModel.progressState.onEach {
            progress.isGone = it
            "initFlow: progress:  $it".myLog("BEHRUZ")
        }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        viewModel.allBookByCategory
            .onEach {
                "initFlow: ${it.size}".myLog("BEHRUZ")
                for (i in it.indices) {
                    it[i].categoryName.myLog("BEHRUZ")
                }
                adapter.submitList(it)
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

}