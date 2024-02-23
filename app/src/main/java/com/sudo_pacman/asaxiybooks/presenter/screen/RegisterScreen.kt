package com.sudo_pacman.asaxiybooks.presenter.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenRegisterBinding
import com.sudo_pacman.asaxiybooks.presenter.viewModel.RegisterVM
import com.sudo_pacman.asaxiybooks.presenter.viewModel.impl.RegisterVMImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterVM by viewModels<RegisterVMImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.parseColor("#0F172B")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.signIn.setOnClickListener {

            val name = binding.fullNameEditView.text.toString()
            val gmail = binding.emailTextView.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.length < 3 || !gmail.endsWith("@gmail.com") || password.length != 8 || name.isEmpty() || gmail.isEmpty() || password.isEmpty())
                Toast.makeText(requireContext(), "Name or gmail or password error ! ", Toast.LENGTH_SHORT).show()
            else {
                viewModel.registerUser(name, password, gmail)
                findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToMainScreen())
            }

        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }


}