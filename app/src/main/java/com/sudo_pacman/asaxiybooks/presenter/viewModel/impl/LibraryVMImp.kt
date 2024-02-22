package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import com.sudo_pacman.asaxiybooks.data.model.BookByCategory
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LibraryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject


@HiltViewModel
class LibraryVMImp @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
): ViewModel(),LibraryVM  {
    override val allBookByCategory = MutableStateFlow<List<BookByCategory>>(
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

    override fun onClickSearch() {
        TODO("Not yet implemented")
    }

    override fun onClickCategory(category: BookByCategory) {
        TODO("Not yet implemented")
    }

    override fun onClickBook(book: BookUIData) {
        TODO("Not yet implemented")
    }

    override fun loadBooks() {
        progressState.value = false
        repository.

    }
}