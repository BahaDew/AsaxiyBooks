package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.navigation.MainScreenPageNavigation
import com.sudo_pacman.asaxiybooks.presenter.screen.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.TabBooksPageVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabBooksPageVMImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: Repository,
    private val mainScreenPageNavigation: MainScreenPageNavigation
) : ViewModel(), TabBooksPageVM {
    override val placeholderState = MutableStateFlow(false)
    override val allDownloadBooks = MutableStateFlow<List<BookUIData>>(arrayListOf())
    override fun requestAllDownloadBooks() {
        repository
    }

    override fun onClickBook(bookUIData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToInfoScreen(bookUIData))
        }
    }

    override fun onClickAddBook() {
        mainScreenPageNavigation.pageTo?.invoke(1)
    }
}