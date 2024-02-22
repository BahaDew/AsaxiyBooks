package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.main.MainScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.AudioPageVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
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

    init {
        viewModelScope.launch {
            repository.booksList.onEach {
                progressSate.value = true
                allCategoryByData.value = booksToSplitCategory(it)
            }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    override fun getAllCategoryByData() {
        progressSate.value = false
        repository.getBooks()
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
            appNavigator.navigateTo(MainScreenDirections.actionMainScreenToInfoScreen())
        }
    }
    private fun booksToSplitCategory(list : List<BookUIData>) : List<CategoryByBookData> {
        val map = HashMap<String, List<BookUIData>>()
//        for (i in list.indices) {
//            if(map.containsKey(list[i]))
//        }
        return arrayListOf()
    }
}