package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBooksData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LibraryVM {
    val allBookByCategory: StateFlow<List<CategoryByBooksData>>
    val errorMessage: Flow<String>
    val progressState: StateFlow<Boolean>

    fun getAllCategoryByData()
    fun onClickSearch()
    fun onClickCategory(category: CategoryByBooksData)
    fun onClickBook(book: BookUIData)


}