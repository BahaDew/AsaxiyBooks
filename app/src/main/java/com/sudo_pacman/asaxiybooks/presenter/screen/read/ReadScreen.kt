package com.sudo_pacman.asaxiybooks.presenter.screen.read

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenReadBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {
    private val binding by viewBinding(ScreenReadBinding::bind)
    private val navArgs by navArgs<ReadScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val book = File.createTempFile("Book", "pdf")
        Firebase.storage.getReferenceFromUrl(navArgs.bookData.bookUrl)
            .getFile(book)
            .addOnSuccessListener {
                Log.d("TTT", "OnSuccess")
                binding.pdfViewer
                    .fromFile(book)
                    .load()
            }


    }
}