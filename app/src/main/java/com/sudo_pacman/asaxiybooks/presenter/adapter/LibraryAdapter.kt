package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sudo_pacman.asaxiybooks.data.model.BookByCategory
import com.sudo_pacman.asaxiybooks.data.model.BookData
import com.sudo_pacman.asaxiybooks.databinding.ScreenInnerItemBinding


class LibraryAdapter: ListAdapter<BookByCategory, LibraryAdapter.LibraryViewHolder>(LibraryDiffUtil) {

    private var onClickBook : ((BookData) -> Unit)? = null
    private var onClickCategory: ((BookByCategory) -> Unit)? = null
    object LibraryDiffUtil : DiffUtil.ItemCallback<BookByCategory>(){
        override fun areItemsTheSame(oldItem: BookByCategory, newItem: BookByCategory): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BookByCategory, newItem: BookByCategory): Boolean {
            return oldItem == newItem
        }

    }

    inner class LibraryViewHolder(val binding : ScreenInnerItemBinding)
        : RecyclerView.ViewHolder(binding.root){

            private val adapter = LibraryInnerAdapter()

            init {
               binding.btnAll.setOnClickListener {
                   onClickCategory?.invoke(getItem(adapterPosition))
               }
                adapter.setOnClickBook {
                    onClickBook?.invoke(it)
                }
            }

            init{
                 binding.rvListInner.adapter = adapter
                 binding.rvListInner.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        fun bind(){
            binding.apply {
                categoryName.text = getItem(adapterPosition).name

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder(
            ScreenInnerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickBook(onClickBook : (BookData) -> Unit){
        this.onClickBook = onClickBook
    }

    fun setOnClickCategory(onClickCategory: (BookByCategory) -> Unit){
        this.onClickCategory = onClickCategory
    }
}





















