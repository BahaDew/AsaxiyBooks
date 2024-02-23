package com.sudo_pacman.asaxiybooks.presenter.screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val navArgs by navArgs<InfoScreenArgs>()
    private val viewModel: InfoViewModel by viewModels<InfoViewModelImpl>()
    private var isResume = false
    private val downloadDialog by lazy(LazyThreadSafetyMode.NONE) { Dialog(requireContext()) }
    private val buyDialog by lazy((LazyThreadSafetyMode.NONE)) { Dialog(requireContext()) }
    private var isBuy = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            Firebase.firestore.collection("category")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach {
                        binding.tvCatogry.text = it.data?.getOrDefault("name", "").toString()
                    }
                }

        }

        val bookData = navArgs.data

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
            if (isBuy) {
                "screen info sotob olingan ekan yuklaymiz".myLog()
                viewModel.downloadBook(bookData)
                downloadDialog()
//                findNavController().navigate(InfoScreenDirections.actionInfoScreenToReadScreen(bookData))
            } else {
                "screen info sotib olish kerak ekab".myLog()
                payDialog()
            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.dismissDownloadDialog.onEach {
            downloadDialog.dismiss()
        }
            .launchIn(lifecycleScope)
    }

    private fun downloadDialog() {
        downloadDialog.setContentView(R.layout.dialog_download)

        downloadDialog.window?.findViewById<ImageView>(R.id.btn_pause)?.setOnClickListener {
            isResume = if (isResume) {
                downloadDialog.window?.findViewById<ImageView>(R.id.btn_pause)!!
                    .setImageResource(R.drawable.ic_restart)
                viewModel.pause()
                true
            } else {
                downloadDialog.window?.findViewById<ImageView>(R.id.btn_pause)!!
                    .setImageResource(R.drawable.ic_pause)
                viewModel.resume()
                false
            }
        }

        downloadDialog.window?.findViewById<ImageView>(R.id.btn_pause)?.setOnClickListener {
            viewModel.pause()
        }

        downloadDialog.window?.findViewById<ImageView>(R.id.btn_cancel)?.setOnClickListener {
            viewModel.cancel()
        }


        downloadDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        downloadDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        downloadDialog.window?.setGravity(Gravity.BOTTOM)

        downloadDialog.show()
    }

    private fun payDialog() {
        buyDialog.setContentView(R.layout.dialog_buy)

        buyDialog.window?.findViewById<AppCompatButton>(R.id.btn_yes_pay)?.setOnClickListener {
            isBuy = true
            binding.btnDownload.text = "Yuklab olish"
            viewModel.buyBook(navArgs.data)
            buyDialog.dismiss()
        }


        buyDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        buyDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        buyDialog.window?.setGravity(Gravity.CENTER)

        buyDialog.show()
    }

    private fun initView() {}
}