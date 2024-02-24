package com.sudo_pacman.asaxiybooks.domain

import com.sudo_pacman.asaxiybooks.data.model.AddBookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun loginUser(password: String, gmail: String): Flow<Result<Boolean>>

    fun registerUser(name: String, gmail: String, password: String): Flow<Result<Unit>>

    fun addBook(data: AddBookData): Flow<Result<Unit>>
}