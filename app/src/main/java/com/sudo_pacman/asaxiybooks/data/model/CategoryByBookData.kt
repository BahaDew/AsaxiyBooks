package com.sudo_pacman.asaxiybooks.data.model

data class CategoryByBookData(
    val categoryId : String,
    val categoryName : String,
    val books : List<BookUIData>
)