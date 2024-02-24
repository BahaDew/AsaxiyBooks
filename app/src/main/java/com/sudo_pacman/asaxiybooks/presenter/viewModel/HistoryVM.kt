package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HistoryVM {

    val historyBooks : StateFlow<List<CategoryByBookData>>
    val errorMessage : Flow<String>
    val progressState: StateFlow<Boolean>


    fun getAllBooks()
    fun onClickBook(bookData: BookUIData)
    fun onClickBack()
}