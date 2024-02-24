package com.sudo_pacman.asaxiybooks.presenter.page

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageProfileBinding
import com.sudo_pacman.asaxiybooks.presenter.viewModel.ProfileVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.ProfileVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfilePage : Fragment(R.layout.page_profile) {
    private val viewModel: ProfileVM by viewModels<ProfileVMImpl>()
    private val binding by viewBinding(PageProfileBinding::bind)
    private val logOutDialog by lazy {  }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initView() = binding.apply {
        btnLogOut.setOnClickListener {
            viewModel.onClickLogOut()
        }
        btnCart.setOnClickListener {
            viewModel.onClickCard()
        }
    }

    private fun initFlow() = binding.apply {
        viewModel.userData
            .onEach {
                userFullName.text = it.name
            }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }
}