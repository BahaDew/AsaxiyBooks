package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.domain.LoginRepository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.LogInScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LoginVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVMImpl @Inject constructor(
    val repository: LoginRepository,
    private val appNavigator: AppNavigator,
) : ViewModel(), LoginVM {

    override val successLoginFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun loginUser(password: String, gmail: String) {
        repository.loginUser(password, gmail).onEach {
            it.onSuccess {
                successLoginFlow.tryEmit(Unit)
            }
            it.onFailure { thr ->
                errorMessage.tryEmit(thr.message ?: "Unknown error")
            }
        }.launchIn(viewModelScope)
    }

    override fun onClickEmail() {
        viewModelScope.launch {
            appNavigator.navigateTo(LogInScreenDirections.actionLogInScreenToRegisterScreen())
        }
    }

}