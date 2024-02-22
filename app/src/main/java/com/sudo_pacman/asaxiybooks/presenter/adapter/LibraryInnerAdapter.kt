package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookData
import com.sudo_pacman.asaxiybooks.databinding.ItemBookBinding

class LibraryInnerAdapter :
    ListAdapter<BookData,LibraryInnerAdapter.LibraryInnerViewHolder>(LibraryInnerDiffUtil) {

        private var onClickBook: ((BookData) -> Unit)? = null
    object LibraryInnerDiffUtil: DiffUtil.ItemCallback<BookData>(){
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

    }

    inner class LibraryInnerViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.book.setOnClickListener{
               // onClickBook?.invoke()
            }
        }
        fun bind(){
             binding.apply {
                 val item = getItem(adapterPosition)
                 nameOfBook.text = item.name
                 nameOfAuthor.text = item.author
                 Glide.with(binding.root.context)
                     .load(item.coverImage.get(0)!!)
                     .centerCrop()
                     .placeholder(R.drawable.ic_logo_1)
                     .error(R.drawable.ic_logo_1)
                     .into(binding.imageOfBook)
             }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryInnerViewHolder {
        return LibraryInnerViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LibraryInnerViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickBook(onClickBook: (BookData) -> Unit){
        this.onClickBook = onClickBook
    }

}