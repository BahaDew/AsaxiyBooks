package com.sudo_pacman.asaxiybooks.presenter.screen.intro.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenItemIntroBinding

class IntroPage : Fragment(R.layout.screen_item_intro) {

    private val binding by viewBinding(ScreenItemIntroBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (requireArguments().getInt("POS", 0)) {

            0 -> {
                binding.image.setImageResource(R.drawable.introduction_1_night)
                binding.title.setText(R.string.elektron_kitoblar)
                binding.text.setText(R.string.elektron_kitob_info)
            }

            1 -> {
                binding.image.setImageResource(R.drawable.introduction_2_night)
                binding.title.text = "Audio kitoblar"
                binding.text.text = "O'zingiz yoqtirgan kitoblarni audio formatda eshitish imkoniyati"
            }

            else -> {
                binding.image.setImageResource(R.drawable.introduction_3_night)
                binding.title.text = "Cheksiz bilim"
                binding.text.text = "O'qing, tinglang, tahlil qiling, fikr bildiring bilimingizni oshiring"
            }

        }
    }


}