package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import com.sudo_pacman.asaxiybooks.navigation.MainScreenPageNavigation
import com.sudo_pacman.asaxiybooks.presenter.viewModel.MainVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class MainVMImpl @Inject constructor(
    private val mainScreenPageNavigation: MainScreenPageNavigation
) : ViewModel(), MainVM {
    override val pageState = channelFlow {
        mainScreenPageNavigation.pageTo = {
            trySend(it)
        }
        awaitClose { mainScreenPageNavigation.pageTo = null }
    }

}