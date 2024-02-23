package com.sudo_pacman.asaxiybooks.presenter.screen

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenAudioBinding
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class AudioScreen : Fragment(R.layout.screen_audio) {

    private val binding by viewBinding(ScreenAudioBinding::bind)
    private var mediaPlayer: MediaPlayer? = null
    private var audioUrl =
        "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/audio%2Fbook_6_audio.mp3?alt=media&token=86936d6f-b3c0-41e9-80a4-16a1d71125f3"

    private var repository = RepositoryImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        repository.booksList.onEach {
//            "List<Book> $it".myLog()
//            audioUrl =
//                if (audioUrl == "1") "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/audio%2Fbook_6_audio.mp3?alt=media&token=86936d6f-b3c0-41e9-80a4-16a1d71125f3"
//                else it[0].audioUrl
//        }.launchIn(lifecycleScope)


        binding.playMusic.setOnClickListener {
//            binding.pauseMusic.visibility = GONE
//            binding.playMusic.visibility = VISIBLE
            Toast.makeText(requireContext(), "Play Music set on click", Toast.LENGTH_SHORT).show()
            playMusic()
        }

//        binding.pauseMusic.setOnClickListener {
//            binding.playMusic.visibility = GONE
//            binding.pauseMusic.visibility = VISIBLE
//            pauseMusic()
//        }

        binding.textStart.text = "?"

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }

        mediaPlayer?.apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
        }
        binding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        GlobalScope.launch(Dispatchers.IO) {
            try {
                mediaPlayer?.setDataSource(audioUrl)
                mediaPlayer?.prepare()
                mediaPlayer?.start()

                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Audio started playing..", Toast.LENGTH_SHORT).show()
                    binding.textEnd.text = milliSecondsToTimer(mediaPlayer!!.duration.toLong())

                    binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (fromUser) mediaPlayer!!.seekTo(progress)
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }

                    })
                }
            } catch (e: IOException) {
                e.printStackTrace()
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Failed to play audio", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
        Toast.makeText(requireContext(), "Audio paused", Toast.LENGTH_SHORT).show()
    }

    private fun milliSecondsToTimer(milliseconds: Long): String {
        var finalTimerString = ""
        var secondsString = ""

        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        return finalTimerString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}