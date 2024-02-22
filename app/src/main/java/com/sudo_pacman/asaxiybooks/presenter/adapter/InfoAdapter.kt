package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.ScreenInfoBinding

class InfoAdapter : ListAdapter<BookUIData, InfoAdapter.InfoHolder>(InfoViewHolder) {

    object InfoViewHolder : DiffUtil.ItemCallback<BookUIData>() {

        override fun areItemsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean = oldItem.docID == newItem.docID

        override fun areContentsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean = oldItem == newItem

    }

    inner class InfoHolder(private val binding: ScreenInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            val data = getItem(adapterPosition)

            Glide.with(binding.root.context)
                .load(data.coverImage)
                .into(binding.imgBook)

            binding.tvBookName.text = data.name
            binding.tvAuthor.text = data.author
            binding.bookDescription.text = data.description

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder = InfoHolder(
        ScreenInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: InfoHolder, position: Int) = holder.bind()

}