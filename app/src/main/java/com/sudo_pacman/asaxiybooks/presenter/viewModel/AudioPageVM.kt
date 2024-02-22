package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import kotlinx.coroutines.flow.StateFlow

interface AudioPageVM {
    val progressSate : StateFlow<Boolean>
    val allCategoryByData : StateFlow<List<CategoryByBookData>>

    fun getAllCategoryByData()

    fun onClickCategory(category: CategoryByBookData)
    fun onClickBook(bookUIData: BookUIData)
}