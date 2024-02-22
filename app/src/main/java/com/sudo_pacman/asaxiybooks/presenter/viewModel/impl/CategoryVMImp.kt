package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import com.sudo_pacman.asaxiybooks.data.model.BookByCategory
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.presenter.viewModel.CategoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CategoryVMImp @Inject constructor(
    private val repository: Repository
): ViewModel(), CategoryVM {
    override val category = MutableStateFlow<BookByCategory?>(null)

    override fun onClickBook(bookUIData: BookUIData) {
        TODO("Not yet implemented")
    }

    override fun onClickBack() {
        TODO("Not yet implemented")
    }
}