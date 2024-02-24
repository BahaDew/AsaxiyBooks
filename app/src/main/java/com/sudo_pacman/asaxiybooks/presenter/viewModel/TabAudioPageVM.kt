package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.StateFlow

interface TabAudioPageVM {
    val placeholderState : StateFlow<Boolean>
    val allDownloadAudioBooks : StateFlow<List<BookUIData>>

    fun requestAllDownloadAudioBooks()

    fun onClickBook(bookUIData: BookUIData)

    fun onClickAddBook()
}