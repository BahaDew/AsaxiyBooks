package com.sudo_pacman.asaxiybooks.presenter.screen.book_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenByCategoryBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.CategoryByBooksAdapter
import com.sudo_pacman.asaxiybooks.presenter.adapter.CategoryPdfByBooksAdapter
import com.sudo_pacman.asaxiybooks.presenter.screen.category_by_audios.CategoryByAudiosScreenArgs
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryByAudioVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.CategoryByAudioVMImpl
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.CategoryVMImp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryBooks : Fragment(R.layout.screen_by_category){
    private val binding by viewBinding(ScreenByCategoryBinding::bind)
    private val adapter = CategoryPdfByBooksAdapter()
    private val navArgs by navArgs<CategoryByAudiosScreenArgs>()
    private val viewModel : CategoryVM by viewModels<CategoryVMImp>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initFlows()
    }

    private fun initFlows() = binding.apply {

    }
    private fun initViews() = binding.apply{
        rvListOnCategory.adapter = adapter
        rvListOnCategory.layoutManager = LinearLayoutManager(requireContext())
        btnBack.setOnClickListener{
            viewModel.onClickBack()
        }

        adapter.onClickBook {bookData ->
            viewModel.onClickBook(bookData)
        }

        adapter.submitList(navArgs.categoryByData.books)
        categoryName.text = navArgs.categoryByData.categoryName


    }



}