package com.sudo_pacman.asaxiybooks.presenter.screen.main.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.PageAudioBinding

class AudioPage : Fragment(R.layout.page_audio) {
    private val binding by viewBinding(PageAudioBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {

    }
}