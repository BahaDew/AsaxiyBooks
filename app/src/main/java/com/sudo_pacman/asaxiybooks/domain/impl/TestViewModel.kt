package com.sudo_pacman.asaxiybooks.domain.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TestViewModel : ViewModel() {
    init {
        val repo = RepositoryAddBook()

        repo.addBook()
            .onEach { "succes".myLog() }
            .launchIn(viewModelScope)
    }
}