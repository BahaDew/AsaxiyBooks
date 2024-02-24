package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.AddBookData
import com.sudo_pacman.asaxiybooks.domain.impl.LoginRepositoryImpl
import com.sudo_pacman.asaxiybooks.presenter.viewModel.RegisterVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterVMImpl @Inject constructor(
    val repository: LoginRepositoryImpl,
) : ViewModel(), RegisterVM {
    override val successLoginFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    override fun registerUser(name: String, password: String, gmail: String) {
        repository.registerUser(name, gmail, password).onEach {
            it.onSuccess {
                successLoginFlow.tryEmit(Unit)
            }
            it.onFailure { thr ->
                errorMessage.tryEmit(thr.message ?: "Unknown error")
            }
        }.launchIn(viewModelScope)
    }

    override fun addBook(data: AddBookData) {
        repository.addBook(data).onEach {
            it.onSuccess {
                successLoginFlow.tryEmit(Unit)
            }
            it.onFailure { thr ->
                errorMessage.tryEmit(thr.message ?: "Unknown error")
            }
        }.launchIn(viewModelScope)
    }


}