package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.UserData
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.ProfileVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVMImpl @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
) : ViewModel(), ProfileVM {
    override val userData = MutableStateFlow(MySharedPreference.getUserData())

    override fun onClickLogOut() {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToLogInScreen())
        }
    }

    override fun onClickCard() {
        viewModelScope.launch {
          appNavigator.navigateTo(MainScreenDirections.actionMainScreenToOrdersHistory())
        }
    }

}