package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.category_by_audios.CategoryByAudiosScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryByAudioVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryByAudioVMImpl @Inject constructor(
    private val repository: Repository,
    private val appNavigator: AppNavigator
) : ViewModel(), CategoryByAudioVM {
    override fun onClickBack() {
        viewModelScope.launch {
            appNavigator.popBackStack()
        }
    }

    override fun onClickBook(bookUIData: BookUIData) {
        viewModelScope.launch {
            appNavigator.navigateTo(CategoryByAudiosScreenDirections.actionCategoryByAudiosScreenToInfoScreen())
        }
    }
}