package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData

interface CategoryVM {
    fun onClickBook(bookUIData: BookUIData)
    fun onClickBack()
}