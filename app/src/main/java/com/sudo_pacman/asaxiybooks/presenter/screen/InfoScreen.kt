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
import androidx.core.view.isVisible
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
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
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
    private val navController by lazy {   findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookData = navArgs.data
        "infoga keldi $bookData".myLog()

        loadData(bookData)
        initView(bookData)
    }


    private fun initView(bookData: BookUIData) {
        viewModel.startScreen(bookData)

        "screen userda bor kitoblar ${MySharedPreference.getUserData().booksId.joinToString(",")}".myLog()

        viewModel.isBoughtSharedFlow.onEach {
            "screen kitob sotib olinganmi $it".myLog()
            binding.btnPay.isVisible = !it

            binding.btnDownload.isVisible = it
        }.launchIn(lifecycleScope)


        viewModel.isReadSharedFlow.onEach {
            "screen info o'qishga tayyor".myLog()
            binding.btnRead.isVisible = it
        }.launchIn(lifecycleScope)

        binding.btnPay.setOnClickListener {
            "screen info click pay".myLog()
            payDialog(bookData)
        }

        binding.btnDownload.setOnClickListener {
            "screen info click download".myLog()
            viewModel.downloadBook(bookData)
            downloadDialog()
        }

        binding.btnRead.setOnClickListener {
            "screen info click read".myLog()
            viewModel.clickRead(bookData)
        }
    }

    private fun loadData(bookData: BookUIData) {
        Glide.with(requireContext())
            .load(bookData.coverImage)
            .placeholder(R.drawable.ic_sand_clock)
            .error(R.drawable.book)
            .into(binding.imgBook)

        binding.tvAuthor.text = bookData.author
        binding.tvBookName.text = bookData.name
        binding.bookDescription.text = bookData.description

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.dismissDownloadDialog.onEach {
            downloadDialog.dismiss()
        }.launchIn(lifecycleScope)

        lifecycleScope.launch {

            Firebase.firestore.collection("category")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.documents.forEach {
                        binding.tvCatogry.text = it.data?.getOrDefault("name", "").toString()
                    }
                }

        }

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

    private fun payDialog(bookData: BookUIData) {
        buyDialog.setContentView(R.layout.dialog_buy)

        buyDialog.window?.findViewById<AppCompatButton>(R.id.btn_yes_pay)?.setOnClickListener {
            viewModel.buyBook(bookData)
            binding.btnDownload.isVisible = true
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

    private fun isDownload(bookData: BookUIData): Boolean {
        return viewModel.isDownload(book = bookData)
    }
}