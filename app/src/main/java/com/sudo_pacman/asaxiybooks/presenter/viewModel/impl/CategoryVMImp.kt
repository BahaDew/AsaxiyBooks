package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.CategoryBooksDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryVMImp @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
): ViewModel(), CategoryVM {


    override fun onClickBook(bookUIData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(CategoryBooksDirections.actionCategoryBooks2ToInfoScreen(bookUIData))
        }
    }

    override fun onClickBack() {
        viewModelScope.launch {
            appNavigator.popBackStack()
        }
    }
}