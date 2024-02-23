package com.sudo_pacman.asaxiybooks.presenter.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenLogInBinding
import com.sudo_pacman.asaxiybooks.presenter.viewModel.LoginVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.LoginVMImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LogInScreen : Fragment(R.layout.screen_log_in) {

    private val binding by viewBinding(ScreenLogInBinding::bind)
    private val viewModel: LoginVM by viewModels<LoginVMImpl>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.parseColor("#0F172B")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initViewModel()
        initButton()

    }

    private fun initButton() {
        binding.apply {

            signIn.setOnClickListener {
//                if (!isValidPassword())return@setOnClickListener
//                if (!isValidGmail())return@setOnClickListener
                viewModel.loginUser(password.text.toString(), emailEditText.text.toString())
            }



            email.setOnClickListener {
                viewModel.onClickEmail()
            }

        }
    }

    private fun initViewModel() {
        viewModel.apply {
            successLoginFlow.onEach {
                findNavController().navigate(LogInScreenDirections.actionLogInScreenToMainScreen())
            }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
            errorMessage.onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
        }
    }

}