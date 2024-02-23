package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow

interface TabBooksPageVM {
    val placeholderState : StateFlow<Boolean>
    val allDownloadBooks : StateFlow<List<BookUIData>>

    fun requestAllDownloadBooks()

    fun onClickBook(bookUIData: BookUIData)

    fun onClickAddBook()
}