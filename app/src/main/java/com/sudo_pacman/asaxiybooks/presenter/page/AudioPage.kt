package com.sudo_pacman.asaxiybooks.presenter.page

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.databinding.PageAudioBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.AudioOuterAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.AudioPageVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.AudioPageVMImpl
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File


@AndroidEntryPoint
class AudioPage : Fragment(R.layout.page_audio) {
    private val binding by viewBinding(PageAudioBinding::bind)
    private val adapter = AudioOuterAdapter()
    private val viewModel: AudioPageVM by viewModels<AudioPageVMImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllCategoryByData()
        "onCreate: getAll".myLog("AUDIO")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initFlow() = binding.apply {
        viewModel.progressSate.onEach {
            progress.isGone = it
            "initFlow: progress:  $it".myLog("AUDIO")
        }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)

        viewModel.allCategoryByData
            .onEach {
                "initFlow: ${it.size}".myLog("AUDIO")
                for (i in it.indices) {
                    it[i].categoryName.myLog("AUDIO")
                }
                adapter.submitList(it)
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    private fun initView() = binding.apply {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnClickBook {
            "initView: book bosildi".myLog("AUDIO")
            viewModel.onClickBook(it)

            adapter.setOnClickBook { data ->

                val book = File.createTempFile(data.name, ".${data.type}")
                Firebase.storage.getReferenceFromUrl(data.audioUrl)
                    .getFile(book)
                    .addOnSuccessListener {
                        book.parent?.myLog()
                        MySharedPreference.setBookInfo(
                            bookId = data.docID,
                            bookLink = "${book.parent}/${book.name}"
                        )
                        "OnSuccess".myLog()
                    }

                    .addOnFailureListener {
                        "${it.message}".myLog()
                        binding.apply {
                        }
                    }

                    .addOnProgressListener {
                        val prot = it.bytesTransferred * 100 / it.totalByteCount
                        "audio &6& $prot".myLog()
                    }

                viewModel.onClickBook(data)

            }

            adapter.setOnClickCategory {
                viewModel.onClickCategory(it)
            }
        }
    }
}