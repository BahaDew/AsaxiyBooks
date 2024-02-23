package com.sudo_pacman.asaxiybooks.domain

import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun loginUser(password: String, gmail: String): Flow<Result<Boolean>>

}