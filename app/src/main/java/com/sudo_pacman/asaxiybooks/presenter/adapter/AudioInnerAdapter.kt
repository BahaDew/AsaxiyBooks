package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.ItemAudioBookBinding

class AudioInnerAdapter : ListAdapter<BookUIData, AudioInnerAdapter.BookHolder>(BookDiffUtil) {
    private var onClickBook : ((BookUIData) -> Unit)? = null
    inner class BookHolder(private val binding : ItemAudioBookBinding) : ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                onClickBook?.invoke(getItem(adapterPosition))
            }
        }
        fun bind() {
            getItem(adapterPosition).apply {
                binding.bookName.text = name
                binding.bookAuthor.text = author
                Glide
                    .with(binding.root.context)
                    .load(coverImage)
                    .error(R.drawable.ic_logo_1)
                    .placeholder(R.drawable.book)
                    .into(binding.imgBook)
                val margRig = if(adapterPosition == currentList.size - 1) 150 else 0
                (binding.boxItem.layoutParams as MarginLayoutParams).apply {
                   marginEnd = margRig
               }
            }
        }
    }
    object BookDiffUtil : DiffUtil.ItemCallback<BookUIData>() {
        override fun areItemsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem.docID == newItem.docID
        }

        override fun areContentsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ItemAudioBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind()
    }
    fun setOnClickBook(onClickBook : (BookUIData) -> Unit) {
        this.onClickBook = onClickBook
    }
}