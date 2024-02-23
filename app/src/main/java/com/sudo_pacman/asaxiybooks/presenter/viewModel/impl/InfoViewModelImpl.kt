package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.UploadData
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryPdf
import com.sudo_pacman.asaxiybooks.presenter.viewModel.InfoViewModel
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val repo: RepositoryPdf
) : ViewModel(), InfoViewModel {

    override val bookSharedFlow: MutableSharedFlow<File> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val progressPercentSharedFlow = MutableStateFlow(0L)


    override fun downloadBook(bookUIData: BookUIData) {
        repo
            .downloadBookWithProgress(bookUIData).onEach {
                when (it) {
                    is UploadData.Progress -> {
                        "model download progress ${it.uploadBytes}".myLog()
                        progressPercentSharedFlow.tryEmit(it.uploadBytes)
                    }

                    is UploadData.Success -> {
                        "model download success".myLog()
                        bookSharedFlow.tryEmit(it.book)
                    }

                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    override fun cancel() {
        repo.cancelDownload()
        "model cancel".myLog()
    }

    override fun pause() {
        repo.pauseDownload()
        "model pause".myLog()
    }

    override fun resume() {
        repo.resumeDownload()
        "model resume".myLog()
    }
}