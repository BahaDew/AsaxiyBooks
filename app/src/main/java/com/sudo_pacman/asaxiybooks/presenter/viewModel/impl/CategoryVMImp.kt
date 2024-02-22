package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryVMImp @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
): ViewModel(), CategoryVM {
    override val category = MutableStateFlow<CategoryByBookData?>(null)

    override fun onClickBook(bookUIData: BookUIData) {
        TODO("Not yet implemented")
    }

    override fun onClickBack() {
       viewModelScope.launch {
         //  appNavigator.navigateTo()
       }
    }
}