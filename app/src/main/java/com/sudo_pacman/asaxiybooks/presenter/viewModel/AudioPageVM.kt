package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBooksData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AudioPageVM {
    val progressSate: StateFlow<Boolean>
    val allCategoryByData: StateFlow<List<CategoryByBooksData>>
    val errorMessage : Flow<String>

    fun getAllCategoryByData()

    fun onClickCategory(category: CategoryByBooksData)
    fun onClickBook(bookUIData: BookUIData)
}