package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LibraryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LibraryVMImp @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
): ViewModel(),LibraryVM  {

    override val allBookByCategory = MutableStateFlow<List<CategoryByBookData>>(
        arrayListOf()
    )

    private var _errorMessage : ((String) -> Unit)? = null
    override val errorMessage = channelFlow {
        _errorMessage = {
            trySend(it)
        }
        awaitClose{ _errorMessage = null}
    }


    override val progressState = MutableStateFlow(true)
    override fun getAllCategoryByData() {
        progressState.value = false
        repository.getCategoryByBooks()
            .onEach {
                progressState.value = true
                it.onSuccess { list ->
                    allBookByCategory.value = list
                }.onFailure { th ->
                    _errorMessage?.invoke(th.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }


    override fun onClickSearch() {
        viewModelScope.launch {
           appNavigator.navigateTo(MainScreenDirections.actionMainScreenToSearchScreen())
        }
    }

    override fun onClickCategory(category: CategoryByBookData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToCategoryBooks2(category))
        }
    }

    override fun onClickBook(book: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToInfoScreen(book))
        }
    }


}