package com.sudo_pacman.asaxiybooks.presenter.viewModel

import com.sudo_pacman.asaxiybooks.data.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface ProfileVM {
    val userData : StateFlow<UserData>
    fun onClickLogOut()
    fun onClickCard()
}