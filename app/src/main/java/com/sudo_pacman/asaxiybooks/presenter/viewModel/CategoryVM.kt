package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import kotlinx.coroutines.flow.StateFlow

interface CategoryVM {
    val category: StateFlow<CategoryByBookData?>

    fun onClickBook(bookUIData: BookUIData)
    fun onClickBack()
}