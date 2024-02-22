package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.ItemBookByCategoryBinding

class CategoryByBooksAdapter : ListAdapter<BookUIData, CategoryByBooksAdapter.CategoryByHolder>(CategoryByDiffUtil) {

    private var onClick : ((BookUIData) -> Unit)? = null
    private var time = System.currentTimeMillis()

    inner class CategoryByHolder(private val binding : ItemBookByCategoryBinding) : ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener{
                if(System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    onClick?.invoke(getItem(adapterPosition))
                }
            }
        }
        fun bind() {
            getItem(adapterPosition).apply {
                binding.bookName.text = name
                binding.bookAuthor.text = author
            }
        }
    }
    object CategoryByDiffUtil : DiffUtil.ItemCallback<BookUIData>() {
        override fun areItemsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem.docID == newItem.docID
        }

        override fun areContentsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryByHolder {
        return CategoryByHolder(
            ItemBookByCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryByHolder, position: Int) {
        holder.bind()
    }
    fun onClickBook(onClick : ((BookUIData) -> Unit)) {
        this.onClick = onClick
    }
}