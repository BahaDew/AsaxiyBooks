package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookByCategory
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.presenter.screen.book_category.CategoryBooks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LibraryVM {
    val allBookByCategory: StateFlow<List<BookByCategory>>
    val errorMessage: Flow<String>
    val progressState: StateFlow<Boolean>

    fun onClickSearch()
    fun onClickCategory(category: BookByCategory)
    fun onClickBook(book: BookUIData)

    fun loadBooks()

}