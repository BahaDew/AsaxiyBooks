package com.sudo_pacman.asaxiybooks.presenter.screen

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.databinding.ScreenAudioBinding
import com.sudo_pacman.asaxiybooks.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AudioScreen : Fragment(R.layout.screen_audio) {

    private val binding by viewBinding(ScreenAudioBinding::bind)
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private var isPlaying = true
    private lateinit var data: BookUIData
    private val navArgs: AudioScreenArgs by navArgs()
//    private lateinit var pref: MySharedPreference


//    private var audioUrl =
//        "https://firebasestorage.googleapis.com/v0/b/asaxiybooks-6f7ed.appspot.com/o/audio%2Fbook_6_audio.mp3?alt=media&token=86936d6f-b3c0-41e9-80a4-16a1d71125f3"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        data = navArgs.data
        seekBar = binding.seekBar

        "Data: $data".myLog()
        "Data audioUrl: ${data.audioUrl}".myLog()

        init()
        controllerSound()


    }

    private fun controllerSound() {

        val book = File(MySharedPreference.getBookLink(bookId = data.docID))
        "controllerSound: ${book.name} or ${book.isFile} or $book".myLog()
        mediaPlayer = MediaPlayer.create(requireContext(), Uri.fromFile(book))
        mediaPlayer?.setOnPreparedListener {
            seekBar.max = mediaPlayer!!.duration
            mediaPlayer!!.start()
            binding.textEnd.text = milliSecondsToTimer(mediaPlayer!!.duration.toLong())
            isPlaying = true

            binding.playMusic.setImageResource(R.drawable.ic_pause)
            startSeekBarUpdate()
        }

        binding.playMusic.setOnClickListener {
            if (mediaPlayer == null) {

                this.mediaPlayer?.setOnCompletionListener {
                    stopPlaying()
                }

                mediaPlayer?.setOnErrorListener { _, _, _ ->
//                    stopPlaying()
                    true
                }

            } else {
                if (!isPlaying) {
                    pausePlaying()
                } else {
                    resumePlaying()
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // progress -> second
                    mediaPlayer?.seekTo(progress)
                    binding.textStart.text = milliSecondsToTimer(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startSeekBarUpdate() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                    binding.textStart.text = milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong())


                } catch (e: Exception) {
                    e.printStackTrace()
//                    stopPlaying()
                }
            }
        }, 0)
    }

    private fun pausePlaying() {
        mediaPlayer?.pause()
        isPlaying = true
        binding.playMusic.setImageResource(R.drawable.ic_play)
    }

    private fun resumePlaying() {
        mediaPlayer?.start()
        isPlaying = false
        binding.playMusic.setImageResource(R.drawable.ic_pause)
    }

    private fun stopPlaying() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = true
        binding.playMusic.setImageResource(R.drawable.ic_play)
        seekBar.progress = 0
        binding.textStart.text = milliSecondsToTimer(0L)
    }

    fun milliSecondsToTimer(milliseconds: Long): String {
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

    fun init() {
        binding.apply {
            Glide.with(requireContext())
                .load(data.coverImage)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        imgBook.setImageResource(R.drawable.book)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }

                }).into(imgBook)

            tvBookName.text = data.name
            tvAuthor.text = data.author

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.stop()
        mediaPlayer = null
    }


}