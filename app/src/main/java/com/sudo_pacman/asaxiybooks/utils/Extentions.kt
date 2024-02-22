package com.sudo_pacman.asaxiybooks.utils

import android.util.Log
import com.sudo_pacman.asaxiybooks.data.entity.EntityBookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData

fun String.myLog(tag: String = "TTT") = Log.d("TTT", this)

fun BookUIData.toEntityBookData(path: String): EntityBookData {
    return EntityBookData(
        id = 0,
        docID = this.docID,
        audioUrl = this.audioUrl,
        author = this.author,
        bookUrl = this.bookUrl,
        categoryId = this.categoryId,
        coverImage = this.coverImage,
        description = this.description,
        filePath = path,
        name = this.name,
        totalSize = this.totalSize,
        type = this.type
    )
}