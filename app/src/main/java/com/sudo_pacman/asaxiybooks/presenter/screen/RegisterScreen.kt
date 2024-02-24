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
import com.sudo_pacman.asaxiybooks.data.model.AddBookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
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
        requireActivity().window.statusBarColor = Color.parseColor("#0F172B")
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
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

//            viewModel.addBook(
//                AddBookData(
//                    "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/audio%2Fbook_7_Kunlar.mp3?alt=media&token=86ec1ae0-4031-4bc0-9398-9cef5d318aa5",
//                    "Abdulla qodiriy",
//                    "",
//                    "CyloVyCdbMKXkJRizgPR",
//                    "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/images%2Fotkan%20kunlar.PNG?alt=media&token=fd504c53-4af0-4847-bc36-c73f2ca7a63a",
//                    "\"O'tkan kunlar” asarini ilk bor ogiganimda, kitobning so'ngida asarga Tohir Malik tomonidan yozilgan sharhni oqib goldim. Ushbu sharhni ogimagunimcha, men “O'tkan kunlar” asarini shunchaki bir sevgi  gissasi, muhabbat hagida yozilgan ilk o’zbek romani deb o'ylagan edim. Ushbu sharhni ogib chiggach asar hagidagi fikrim butunlay o’zgardi va asar yozilishining asl magsadi va uning ostida yotgan xalq  dardini tushunib yetdim. Sizga ham Tohir Malik tomonidan \"O’tkan kunlar” romani hagida yozilgan mazkur magolani oqishni tavsiya gilaman.",
//                    "",
//                    "O'tkan kunlar",
//                    "1.83",
//                    "mp3"
//                )
//            )

        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}