package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBooksData
import com.sudo_pacman.asaxiybooks.databinding.ItemInnerRvAudioBinding
import com.sudo_pacman.asaxiybooks.utils.myLog

class AudioOuterAdapter : ListAdapter<CategoryByBooksData, AudioOuterAdapter.CategoryHolder>(CategoryDiffUtil) {
    private var onClickBook : ((BookUIData) -> Unit)? = null
    private var onClickCategory : ((CategoryByBooksData) -> Unit)? = null
    private var time = System.currentTimeMillis()

    inner class CategoryHolder(private val binding : ItemInnerRvAudioBinding) : ViewHolder(binding.root) {
        private val adapter = AudioInnerAdapter()
        init {
            binding.btnAll.setOnClickListener{
                if(System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    "adapter bosildi category ".myLog("ADAPTER")
                    onClickCategory?.invoke(getItem(adapterPosition))
                }
            }
            adapter.setOnClickBook {
                if(System.currentTimeMillis() - time >= 500) {
                    time = System.currentTimeMillis()
                    onClickBook?.invoke(it)
                    Log.d("TTT", "Bosil")
                }
            }
            binding.rvList.adapter = adapter
            binding.rvList.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
        }
        fun bind() {
            getItem(adapterPosition).apply {
                binding.categoryName.text = categoryName
                adapter.submitList(books)
            }
        }
    }

    object CategoryDiffUtil : DiffUtil.ItemCallback<CategoryByBooksData>() {
        override fun areItemsTheSame(
            oldItem: CategoryByBooksData,
            newItem: CategoryByBooksData
        ): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(
            oldItem: CategoryByBooksData,
            newItem: CategoryByBooksData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            ItemInnerRvAudioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind()
    }
    fun setOnClickBook(onClickBook : (BookUIData) -> Unit) {
        this.onClickBook = onClickBook
    }

    fun setOnClickCategory(onClickCategory : (CategoryByBooksData) -> Unit) {
        this.onClickCategory = onClickCategory
    }
}