package com.sudo_pacman.asaxiybooks.presenter.screen.category_by_audios

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenCategoryByAduioBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.CategoryByBooksAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryByAudioVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.CategoryByAudioVMImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryByAudiosScreen : Fragment(R.layout.screen_category_by_aduio) {
    private val binding by viewBinding(ScreenCategoryByAduioBinding::bind)
    private val adapter = CategoryByBooksAdapter()
    private val navArgs by navArgs<CategoryByAudiosScreenArgs>()
    private val viewModel: CategoryByAudioVM by viewModels<CategoryByAudioVMImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initFlow() = binding.apply {

    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(requireContext())
        btnBack.setOnClickListener {
            viewModel.onClickBack()
        }
        adapter.onClickBook {
            viewModel.onClickBook(it)
        }
        adapter.submitList(navArgs.categoryByData.books)
        categoryName.text = navArgs.categoryByData.categoryName
    }
}