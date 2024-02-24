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
    val dismissDownloadDialog: MutableSharedFlow<Unit>

    fun buyBook(book: BookUIData)
    fun startScreen(book: BookUIData)
    val isBoughtSharedFlow: MutableSharedFlow<Boolean>
}