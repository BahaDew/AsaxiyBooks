package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBooksData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.OrdersHistoryDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.HistoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryVMImp @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
): ViewModel(), HistoryVM {
    override val historyBooks = MutableStateFlow<List<CategoryByBooksData>>(arrayListOf())

    var _errorMessage: ((String) -> Unit)? = null
    override val errorMessage = channelFlow{
        _errorMessage = {
            trySend(it)
        }
        awaitClose{ _errorMessage = null}
    }
    override val progressState = MutableStateFlow(false)

    override fun getAllBooks() {
        repository.getCategoryByAudioBooks().onEach {
            progressState.value = true
            it.onSuccess { list ->
                historyBooks.value = list
            }
            it.onFailure {
                _errorMessage?.invoke(it.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun onClickBook(bookData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(OrdersHistoryDirections.actionOrdersHistoryToInfoScreen(bookData))
        }
    }

    override fun onClickBack() {
        viewModelScope.launch {
            appNavigator.popBackStack()
        }
    }
}