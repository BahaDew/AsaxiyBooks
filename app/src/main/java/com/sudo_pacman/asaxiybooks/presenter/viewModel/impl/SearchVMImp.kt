package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.SearchScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.SearchVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchVMImp @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: Repository

): ViewModel(), SearchVM {
    override val resultBooks = MutableStateFlow<List<BookUIData>>(arrayListOf())

    override fun textChange(text: String) {
        resultBooks.value = repository.getBookByName(text)
    }

    override fun onClickBook(bookData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(SearchScreenDirections.actionSearchScreenToInfoScreen(bookData))
        }
    }

    override fun onClickBack() {
        viewModelScope.launch {
            appNavigator.popBackStack()
        }
    }
}