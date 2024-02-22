package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.databinding.ScreenInnerItemBinding


class LibraryAdapter: ListAdapter<CategoryByBookData, LibraryAdapter.LibraryViewHolder>(LibraryDiffUtil) {

    private var onClickBook : ((BookUIData) -> Unit)? = null
    private var onClickCategory: ((CategoryByBookData) -> Unit)? = null
    object LibraryDiffUtil : DiffUtil.ItemCallback<CategoryByBookData>(){
        override fun areItemsTheSame(oldItem: CategoryByBookData, newItem: CategoryByBookData): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(oldItem: CategoryByBookData, newItem: CategoryByBookData): Boolean {
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
                categoryName.text = getItem(adapterPosition).categoryName

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

    fun setOnClickBook(onClickBook : (BookUIData) -> Unit){
        this.onClickBook = onClickBook
    }

    fun setOnClickCategory(onClickCategory: (CategoryByBookData) -> Unit){
        this.onClickCategory = onClickCategory
    }
}





















