package com.sudo_pacman.asaxiybooks.presenter.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.databinding.ScreenMainBinding
import com.sudo_pacman.asaxiybooks.presenter.adapter.MainVPAdapter

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private lateinit var adapter: MainVPAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initFlow()
    }

    private fun initView() = binding.apply {
        adapter = MainVPAdapter(childFragmentManager, lifecycle)
        vp2.adapter = adapter
        vp2.isUserInputEnabled = false
        bnv.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.books -> vp2.setCurrentItem(0, false)
                R.id.library -> vp2.setCurrentItem(1, false)
                R.id.audio -> vp2.setCurrentItem(2, false)
                else -> vp2.setCurrentItem(3, false)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initFlow() = binding.apply {

    }
}