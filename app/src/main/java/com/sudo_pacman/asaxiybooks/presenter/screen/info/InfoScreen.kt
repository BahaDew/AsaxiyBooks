package com.sudo_pacman.asaxiybooks.presenter.screen.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenInfoBinding
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val navArgs by navArgs<InfoScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookData = navArgs.book

        "infoga keldi $bookData".myLog()

        Glide.with(requireContext())
            .load(bookData.coverImage)
            .placeholder(R.drawable.ic_sand_clock)
            .error(R.drawable.book)
            .into(binding.imgBook)

        binding.tvAuthor.text = bookData.author
        binding.tvBookName.text = bookData.name
        binding.bookDescription.text = bookData.description

        binding.btnDownload.setOnClickListener {
            findNavController().navigate(InfoScreenDirections.actionInfoScreenToReadScreen(bookData))
        }
    }
}