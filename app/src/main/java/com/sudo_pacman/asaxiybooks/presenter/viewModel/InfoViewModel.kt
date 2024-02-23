package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

interface InfoViewModel {

    fun downloadBook(bookUIData: BookUIData)
    fun pause()
    fun cancel()
    fun resume()

    val bookSharedFlow: MutableSharedFlow<File>
    val progressPercentSharedFlow: MutableStateFlow<Long>
    fun buyBook(book: BookUIData)
    val dismissDownloadDialog: MutableSharedFlow<Unit>
}