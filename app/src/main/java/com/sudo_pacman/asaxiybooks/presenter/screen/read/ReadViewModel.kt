package com.sudo_pacman.asaxiybooks.presenter.screen.read

import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

interface ReadViewModel {
    fun downloadBook(bookUIData: BookUIData)
    val bookSharedFlow: MutableSharedFlow<File>
}