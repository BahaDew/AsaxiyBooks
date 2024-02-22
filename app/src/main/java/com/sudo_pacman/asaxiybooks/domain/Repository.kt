package com.sudo_pacman.asaxiybooks.domain

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface Repository {
    val booksList: MutableSharedFlow<List<BookUIData>>
    val categoriesList: MutableSharedFlow<Result<List<CategoryByBookData>>>
    val bookLoadError: MutableSharedFlow<String>
    fun getBooks()
    fun getCategoryByBooks()
    fun setData(books: List<BookUIData>): Flow<Result<Unit>>

}