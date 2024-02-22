package com.sudo_pacman.asaxiybooks.presenter.screen.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryPdf
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReadViewModelImpl @Inject constructor(
    private val repo: RepositoryPdf
) : ViewModel(), ReadViewModel {
    override val bookSharedFlow = MutableSharedFlow<File>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun downloadBook(bookUIData: BookUIData) {

        repo.downloadBook(bookUIData).onEach { result ->
            result.onSuccess {
                "model download book success".myLog()
                bookSharedFlow.tryEmit(it)
            }

            result.onFailure {
                "model download book fail ${it.message}".myLog()
            }
        }
        .launchIn(viewModelScope)
    }

}