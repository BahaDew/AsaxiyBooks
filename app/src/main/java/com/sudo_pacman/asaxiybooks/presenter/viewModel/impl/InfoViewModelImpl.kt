package com.sudo_pacman.asaxiybooks.presenter.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.UploadData
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryPdf
import com.sudo_pacman.asaxiybooks.navigation.AppNavigation
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import com.sudo_pacman.asaxiybooks.presenter.screen.InfoScreenDirections
import com.sudo_pacman.asaxiybooks.presenter.viewModel.InfoViewModel
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    private val repo: RepositoryPdf,
    private val appNavigator: AppNavigator
) : ViewModel(), InfoViewModel {

    override val bookSharedFlow: MutableSharedFlow<File> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val bookReadSharedFlow: MutableSharedFlow<File> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val progressPercentSharedFlow = MutableStateFlow(0L)
    override val dismissDownloadDialog: MutableSharedFlow<Unit> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val isBoughtSharedFlow = MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val isReadSharedFlow = MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun downloadBook(bookUIData: BookUIData) {
        "repo download qilish kerak: ${bookUIData.bookUrl}".myLog()
        repo
            .downloadBookWithProgress(bookUIData).onEach {
                when (it) {
                    is UploadData.Progress -> {
                        "model download progress ${it.uploadBytes}".myLog()
                        progressPercentSharedFlow.tryEmit(it.uploadBytes)
                    }

                    is UploadData.Success -> {
                        "model download success".myLog()
//                        bookSharedFlow.tryEmit(it.book)
                        isReadSharedFlow.tryEmit(true)
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

    override fun buyBook(book: BookUIData) {
        repo.buyBook(book)
            .onEach {
                isBoughtSharedFlow.tryEmit(true)
            }.launchIn(viewModelScope)
    }

    override fun startScreen(book: BookUIData) {

        val isBought = repo.isBought(book)

        if (isBought) {
            "model info bu kitob sotib olingan".myLog()
            isDownload(book)
        } else {
            "model info bu kitob sotib olinmagan ekan".myLog()
        }

        isBoughtSharedFlow.tryEmit(isBought)
    }

    override fun isDownload(book: BookUIData): Boolean {
        "model info bu kitob yuklab olinganmi".myLog()
        isReadSharedFlow.tryEmit(repo.isDownload(book))
        return repo.isDownload(book)
    }

    override fun clickRead(bookData: BookUIData) {
        "model click read".myLog()

        viewModelScope.launch {
            appNavigator.navigateTo(InfoScreenDirections.actionInfoScreenToReadScreen(repo.getBook(bookData).absolutePath))
        }
    }
}































