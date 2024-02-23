package com.sudo_pacman.asaxiybooks.presenter.screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().window.statusBarColor = Color.parseColor("#0F172B")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        binding.signIn.setOnClickListener {
            val email = binding.phone.text.toString()
            val password = binding.name.text.toString()

//            if (email.isEmpty() || password.isEmpty()) Toast.makeText(requireContext(), "Email or password error!", Toast.LENGTH_SHORT).show()
//            else logIN(email, password)
            findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToMainScreen())
        }


    }

    private fun logIN(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToMainScreen())
                else Toast.makeText(requireContext(), "log in error bro!", Toast.LENGTH_SHORT).show()
            }
    }

}