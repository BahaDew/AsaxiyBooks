package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.navigation.MainScreenPageNavigation
import com.sudo_pacman.asaxiybooks.presenter.screen.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.TabAudioPageVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabAudioPageVMImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: Repository,
    private val mainScreenPageNavigation: MainScreenPageNavigation
) : ViewModel(), TabAudioPageVM {
    override val placeholderState = MutableStateFlow<Boolean>(false)
    override val allDownloadAudioBooks = MutableStateFlow<List<BookUIData>>(arrayListOf())

    override fun requestAllDownloadAudioBooks() {
        repository.getDownloadAudioBooksData()
            .onEach {
                it.onSuccess { list ->
                    allDownloadAudioBooks.value = list
                    placeholderState.value = list.isNotEmpty()
                }.onFailure {

                }
            }.launchIn(viewModelScope)
    }

    override fun onClickBook(bookUIData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToAudioScreen(bookUIData))
        }
    }

    override fun onClickAddBook() {
        mainScreenPageNavigation.pageTo?.invoke(1)
    }
}