package com.sudo_pacman.asaxiybooks.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryByBookData(
    val categoryId : String,
    val categoryName : String,
    val books : List<BookUIData>,
    val type: Int
) : Parcelable