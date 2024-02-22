package com.sudo_pacman.asaxiybooks.presenter.screen.audio

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenAudioBinding
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AudioScreen : Fragment(R.layout.screen_audio) {

    private val binding by viewBinding(ScreenAudioBinding::bind)
    private lateinit var mediaPlayer: MediaPlayer
    private var audioUrl = ""
    private var repository = RepositoryImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mediaPlayer = MediaPlayer()

        binding.playMusic.setOnClickListener {

            repository.getBooks()

            repository.booksList.onEach {
                audioUrl = it[0].audioUrl
            }.launchIn(lifecycleScope)


            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            try {
                mediaPlayer.setDataSource(audioUrl)

                mediaPlayer.prepare()

                mediaPlayer.start()

            } catch (e: Exception) {

                e.printStackTrace()
            }
            Toast.makeText(requireContext(), "Audio started playing..", Toast.LENGTH_SHORT).show()

        }

    }

}