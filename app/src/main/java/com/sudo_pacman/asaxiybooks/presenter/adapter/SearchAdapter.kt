package com.sudo_pacman.asaxiybooks.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sudo_pacman.asaxiybooks.R
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.databinding.ItemPdfBookByCategoryBinding

class SearchAdapter : ListAdapter<BookUIData, SearchAdapter.SearchViewHolder>(SearchDiffUtil) {

    private var onClickBook: ((BookUIData) -> Unit)? = null
    private var time = System.currentTimeMillis()
    inner class SearchViewHolder(private val binding: ItemPdfBookByCategoryBinding)
        :RecyclerView.ViewHolder(binding.root){

            init {
                binding.boxItem.setOnClickListener {
                    if(System.currentTimeMillis() - time >= 500){
                        time = System.currentTimeMillis()
                        onClickBook?.invoke(getItem(adapterPosition))
                    }
                }
            }
            fun bind(){
                binding.apply {
                    val item = getItem(adapterPosition)
                    bookName.text = item.name
                    bookAuthor.text = item.author

                    Glide.with(binding.root.context)
                        .load(item.coverImage[0])
                        .centerCrop()
                        .placeholder(R.drawable.ic_logo_1)
                        .error(R.drawable.ic_logo_1)
                        .into(binding.imgBook)
                }
            }
        }

    object SearchDiffUtil : DiffUtil.ItemCallback<BookUIData>(){
        override fun areItemsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BookUIData, newItem: BookUIData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder = SearchViewHolder(
       ItemPdfBookByCategoryBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickBook(onClickBook: (BookUIData) -> Unit){
        this.onClickBook = onClickBook
    }


}