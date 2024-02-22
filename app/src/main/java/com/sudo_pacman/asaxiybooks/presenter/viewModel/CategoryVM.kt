package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookByCategory
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow

interface CategoryVM {
    val category: StateFlow<BookByCategory?>

    fun onClickBook(bookUIData: BookUIData)
    fun onClickBack()
}