package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBooksData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HistoryVM {

    val historyBooks : StateFlow<List<CategoryByBooksData>>
    val errorMessage : Flow<String>
    val progressState: StateFlow<Boolean>


    fun getAllBooks()
    fun onClickBook(bookData: BookUIData)
    fun onClickBack()
}