package com.sudo_pacman.asaxiybooks.data

import com.sudo_pacman.asaxiybooks.data.entity.EntityBookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData

fun EntityBookData.toUiData() : BookUIData {
    return BookUIData(
        docID = this.docID,
        audioUrl = this.audioUrl,
        author = this.author,
        bookUrl = this.bookUrl,
        categoryId = this.categoryId,
        coverImage = this.coverImage,
        description = this.description,
        filePath = this.filePath,
        name = this.name,
        totalSize = this.totalSize,
        type = this.type
    )
}