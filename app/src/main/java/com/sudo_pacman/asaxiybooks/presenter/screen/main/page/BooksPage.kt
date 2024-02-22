package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.PageBooksBinding
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryImpl
import com.sudo_pacman.asaxiybooks.presenter.screen.main.MainScreenDirections
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BooksPage : Fragment(R.layout.page_books) {
    private val binding by viewBinding(PageBooksBinding::bind)
    private val repository = RepositoryImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBook.setOnClickListener {
            findNavController().navigate(
                MainScreenDirections.actionMainScreenToInfoScreen(
                    BookUIData(
                        docID = "test",
                        author = "Mario Puzo",
                        bookUrl = "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/books%2Fbook6.pdf?alt=media&token=7a6242e5-e183-4711-b736-e2eca1199738",
                        categoryId = "CyloVyCdbMKXkJRizgPR",
                        coverImage = "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/images%2Fcho'qintirgan%20ota.PNG?alt=media&token=0dc20354-691a-42e0-9ea2-e22b4be66f18",
                        description = "info",
                        filePath = "",
                        name = "Cho'qintirgan ota",
                        totalSize = "2.75",
                        type = "pdf",
                        audioUrl = "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/audio%2Fbook_6_audio.mp3?alt=media&token=86936d6f-b3c0-41e9-80a4-16a1d71125f3"
                    )
                )
            )
        }


//        binding.addBook.setOnClickListener {
//            findNavController().navigate(MainScreenDirections.actionMainScreenToInfoScreen())
//        }

    }
}