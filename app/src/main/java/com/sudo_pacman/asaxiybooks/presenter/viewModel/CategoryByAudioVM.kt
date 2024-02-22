package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData

interface CategoryByAudioVM {

    fun onClickBack()
    fun onClickBook(bookUIData: BookUIData)
}