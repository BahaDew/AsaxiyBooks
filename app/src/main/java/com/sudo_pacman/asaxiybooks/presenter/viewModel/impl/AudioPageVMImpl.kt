package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.AudioPageVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioPageVMImpl @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
) : ViewModel(), AudioPageVM {
    override val progressSate = MutableStateFlow(true)
    override val allCategoryByData = MutableStateFlow<List<CategoryByBookData>>(arrayListOf())
    private var _errorMessage: ((String) -> Unit)? = null
    override val errorMessage: Flow<String> = channelFlow {
        _errorMessage = {
            trySend(it)
        }
        awaitClose { _errorMessage = null }
    }

    override fun getAllCategoryByData() {
        progressSate.value = false
        repository.getCategoryByBooks()
            .onEach {
                progressSate.value = true
                it.onSuccess { list ->
                    allCategoryByData.value = list
                }.onFailure { th ->
                    _errorMessage?.invoke(th.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onClickCategory(category: CategoryByBookData) {
        viewModelScope.launch {
            appNavigator.navigateTo(
                MainScreenDirections.actionMainScreenToCategoryByAudiosScreen(
                    category
                )
            )
        }
    }

    override fun onClickBook(bookUIData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToInfoScreen(bookUIData))
        }
    }
}