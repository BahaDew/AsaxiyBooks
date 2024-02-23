package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow

interface SearchVM {
    val resultBooks : StateFlow<List<BookUIData>>

    fun textChange(text: String)
    fun onClickBook(bookData: BookUIData)
    fun onClickBack()
}