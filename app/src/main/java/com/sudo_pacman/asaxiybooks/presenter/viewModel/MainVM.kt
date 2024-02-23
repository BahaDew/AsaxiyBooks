package com.sudo_pacman.asaxiybooks.presenter.viewModel

import kotlinx.coroutines.flow.Flow


interface MainVM {
    val pageState : Flow<Int>
}