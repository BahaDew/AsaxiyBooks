package com.sudo_pacman.asaxiybooks.presenter.viewModel

import kotlinx.coroutines.flow.Flow

interface LoginVM {
    val successLoginFlow: Flow<Unit>
    val errorMessage: Flow<String>
    fun loginUser(password: String, gmail: String)
}