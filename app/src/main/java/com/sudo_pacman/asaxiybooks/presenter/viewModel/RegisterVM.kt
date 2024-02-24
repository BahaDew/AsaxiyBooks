package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.AddBookData
import kotlinx.coroutines.flow.Flow

interface RegisterVM {
    val successLoginFlow: Flow<Unit>
    val errorMessage: Flow<String>
    fun registerUser(name: String, password: String, gmail: String)
    fun addBook(data: AddBookData)
}