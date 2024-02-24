package com.sudo_pacman.asaxiybooks.presenter.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenMainBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.MainVPAdapter
import com.sudo_pacman.asaxiybooks.presenter.viewModel.MainVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.MainVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private lateinit var adapter: MainVPAdapter
    private val viewModel: MainVM by viewModels<MainVMImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = Color.parseColor("#0F172B")
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initView() = binding.apply {
        adapter = MainVPAdapter(childFragmentManager, lifecycle)
        vp2.adapter = adapter
        vp2.isUserInputEnabled = false
        bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.books -> vp2.setCurrentItem(0, false)
                R.id.library -> vp2.setCurrentItem(1, false)
                R.id.audio -> vp2.setCurrentItem(2, false)
                else -> vp2.setCurrentItem(3, false)
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun initFlow() = binding.apply {
        viewModel.pageState
            .onEach {
                vp2.currentItem = it
                bnv.selectedItemId = R.id.library
            }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}