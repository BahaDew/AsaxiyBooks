package com.sudo_pacman.asaxiybooks.presenter.viewModel

import kotlinx.coroutines.flow.Flow

interface RegisterVM {
    val successLoginFlow: Flow<Unit>
    val errorMessage: Flow<String>
    fun registerUser(name: String, password: String, gmail: String)
}