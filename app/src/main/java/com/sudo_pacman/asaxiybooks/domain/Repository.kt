package com.sudo_pacman.asaxiybooks.domain

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface Repository {
    val booksList: MutableSharedFlow<List<BookUIData>>
    val bookLoadError: MutableSharedFlow<String>
    fun getBooks()
    fun setData(books: List<BookUIData>): Flow<Result<Unit>>

}