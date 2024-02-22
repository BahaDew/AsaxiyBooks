package com.sudo_pacman.asaxiybooks.domain

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getBooks()
    fun setData(books: List<BookUIData>): Flow<Result<Unit>>


}