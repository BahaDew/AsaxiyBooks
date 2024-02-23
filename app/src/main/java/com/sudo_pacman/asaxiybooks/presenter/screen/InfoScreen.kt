package com.sudo_pacman.asaxiybooks.presenter.screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenInfoBinding
import com.sudo_pacman.asaxiybooks.presenter.viewModel.InfoViewModel
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.InfoViewModelImpl
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val navArgs by navArgs<InfoScreenArgs>()
    private val viewModel: InfoViewModel by viewModels<InfoViewModelImpl>()
    private var isResume = false
    private val bottomDialog = Dialog(requireContext())

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
            viewModel.downloadBook(bookData)
            dialog()
//            findNavController().navigate(InfoScreenDirections.actionInfoScreenToReadScreen(bookData))
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        Firebase.firestore.collection("category")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.documents.forEach {
                    binding.tvCatogry.text = it.data?.getOrDefault("name", "").toString()
                }
            }
    }

    private fun dialog() {
        bottomDialog.setContentView(R.layout.dialog_download)

        bottomDialog.window?.findViewById<ImageView>(R.id.btn_pause)?.setOnClickListener {
            isResume = if (isResume) {
                bottomDialog.window?.findViewById<ImageView>(R.id.btn_pause)!!.setImageResource(R.drawable.ic_restart)
                viewModel.pause()
                true
            } else {
                bottomDialog.window?.findViewById<ImageView>(R.id.btn_pause)!!.setImageResource(R.drawable.ic_pause)
                viewModel.resume()
                false
            }
        }

        bottomDialog.window?.findViewById<ImageView>(R.id.btn_pause)?.setOnClickListener {
            viewModel.cancel()
        }


        bottomDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        bottomDialog.window?.setGravity(Gravity.BOTTOM)

        bottomDialog.show()
    }
}